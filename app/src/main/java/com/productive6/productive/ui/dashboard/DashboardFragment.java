package com.productive6.productive.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.objects.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Inject
    ITaskManager taskManager;
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
    private void attachTaskView(View root){
        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView);//Grab display
        taskAdapter = new TaskAdapter(taskManager, root);
        taskAdapter.setTasks(new ArrayList<>());
        taskDisplayView.setAdapter(taskAdapter);//attach display to view + tasks

        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        EventDispatch.registerListener(taskAdapter);
        taskManager.getTasksByPriority(new TaskConsumerStartup(taskAdapter));//Logic CALL--Load Tasks

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
                System.out.println("1");
                break;
            case "Due Date":
                System.out.println("2");
                break;
            case "Created Date":
                System.out.println("3");
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