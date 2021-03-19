package com.productive6.productive.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.util.DateUtilities;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCompleteEvent;
import com.productive6.productive.ui.stats.StatsActivity;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.productive6.productive.objects.events.task.TaskCreateEvent;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.ui.stats.StatsActivity;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener, ProductiveListener {

    @Inject
    ITaskManager taskManager;
    @Inject
    ITaskSorter taskSorter;
    private TaskAdapter taskAdapter;
    private static String sortingBySelection;
    private static int sortBySelectionInt;
    private Spinner sortBySpinner;

    /**
     * Creates parent view for the tasks
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        sortBySpinner = (Spinner) root.findViewById(R.id.sortTasksDropdown);

        List<String> itemsList = new ArrayList<>();
        itemsList.add("Task Priority");
        itemsList.add("Created Date");
        itemsList.add("Due Date");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, itemsList);
        stringArrayAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        sortBySpinner.setAdapter(stringArrayAdapter);


        if(sortingBySelection !=null) {//Set the spinner to where it was left the last time the user was on the page.
            sortBySpinner.setSelection(sortBySelectionInt);
        }
        attachTaskView(root);
        sortBySpinner.setOnItemSelectedListener(this);
        return root;
    }


    /**
     * Attaches task data, view, and display. Allowing for dynamically rendered tasks in the task display.
     *
     * @param root
     */
    private void attachTaskView(View root) {
        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView);//Grab display
        taskAdapter = new TaskAdapter(taskManager, root);
        taskAdapter.setTasks(new ArrayList<>());
        taskDisplayView.setAdapter(taskAdapter);//attach display to view + tasks
        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        EventDispatch.registerListener(this);
        EventDispatch.registerListener(taskAdapter);

        sortAndDisplayTasks();

        taskSorter.getTasksByPriority((taskAdapter::setTasks));//logic call:: get tasks, provide it to task adapter

        root.findViewById(R.id.buttonBarChart).setOnClickListener(view -> startActivity(new Intent(getContext(), StatsActivity.class)));

        Button createButton = root.findViewById(R.id.newTaskButton);
        createButton.setOnClickListener(v -> {
            new TaskPopup(getContext(), null, taskManager::addTask).open(v);
        });
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(TaskCreateEvent e){
                sortAndDisplayTasks();
            }

            @ProductiveEventHandler
            public void onUpdateTask(TaskUpdateEvent e) {
                if(!e.getTask().isCompleted()){
                    sortAndDisplayTasks();
                }

            }
        });
    }

    /**
     * Sorts by spinner selection and sets the tasks in the taskAdapter.
     * This is the way to update the task view.
     */
    private void sortAndDisplayTasks(){
        if(sortingBySelection != null) {// When first started, sortBySelection will be null, so get by priority is chosen by default.
            switch (sortingBySelection) {
                case "Task Priority":
                    taskSorter.getTasksByPriority(new TaskConsumerStartup(taskAdapter, sortBySpinner));
                    break;
                case "Due Date":
                    taskSorter.getTasksByDueDate(new TaskConsumerStartup(taskAdapter, sortBySpinner));
                    break;
                case "Created Date":
                    taskSorter.getTasksByCreation(new TaskConsumerStartup(taskAdapter, sortBySpinner));
                    break;
            }
        }else{
            taskSorter.getTasksByPriority(new TaskConsumerStartup(taskAdapter, sortBySpinner));
        }

    }
    /**
     * Reacts to changes made to the sort-by dropdown.
     * Uses a switch statement to perform different actions based on what the dropdown was changed to.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();

        sortingBySelection = selection;
        this.sortBySelectionInt = position;
        sortAndDisplayTasks();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;//Do nothing when nothing is selected
    }

    /**
     * Holds a callback for the database request made in attachTaskView.
     */
    public static class TaskConsumerStartup implements Consumer<List<Task>> {
        private final TaskAdapter taskAdapter;
        private final Spinner sort_by;

        TaskConsumerStartup(TaskAdapter taskAdapter, Spinner sort_by){this.taskAdapter = taskAdapter;this.sort_by=sort_by;}

        @Override
        public void accept(List<Task> tasks) {
            taskAdapter.setTasks(tasks);
            if(taskAdapter.getItemCount()!=0){
                sort_by.setEnabled(true);
                sort_by.setClickable(true);
            }else{
                sort_by.setEnabled(false);
                sort_by.setClickable(false);
            }
            //Give data to Task list and automatically re-renders the Task list view
        }
    }


}