package com.productive6.productive.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Inject
    ITaskManager taskManager;
    @Inject
    ITaskSorter taskSorter;
    private TaskAdapter taskAdapter;

    /**
     * Creates parent view for the tasks
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Spinner sort_by = (Spinner) root.findViewById(R.id.sortTasksDropdown);

        attachTaskView(root);
        sort_by.setOnItemSelectedListener(this);
        return root;
    }

    /**
     * Attaches task data, view, and display. Allowing for dynamically rendered tasks in the task display.
     * @param root
     */
    private void attachTaskView(View root) {
        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView);//Grab display
        taskAdapter = new TaskAdapter(taskManager, root);
        taskAdapter.setTasks(new ArrayList<>());
        taskDisplayView.setAdapter(taskAdapter);//attach display to view + tasks

        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        EventDispatch.registerListener(taskAdapter);

        taskSorter.getTasksByPriority((taskAdapter::setTasks));//logic call:: get tasks, provide it to task adapter
        Button createButton = root.findViewById(R.id.newTaskButton);
        createButton.setOnClickListener(v -> {
            new TaskPopup(getContext(), null, taskManager::addTask).open(v);
        });
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
        switch(selection){
            case "Priority":
                taskSorter.getTasksByPriority(new TaskConsumerStartup(taskAdapter));
                break;
            case "Due Date":
                taskSorter.getTasksByDueDate(new TaskConsumerStartup(taskAdapter));
                break;
            case "Created Date":
                taskSorter.getTasksByCreation(new TaskConsumerStartup(taskAdapter));
                break;
        }
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

        TaskConsumerStartup(TaskAdapter taskAdapter){this.taskAdapter = taskAdapter;}

        @Override
        public void accept(List<Task> tasks) {
            taskAdapter.setTasks(tasks);
            //Give data to Task list and automatically re-renders the Task list view
        }
    }

}