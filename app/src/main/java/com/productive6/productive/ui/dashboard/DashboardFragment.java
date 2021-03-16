package com.productive6.productive.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class DashboardFragment extends Fragment {

    @Inject
    ITaskManager taskManager;


    /**
     * Creates parent view for the tasks
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        attachTaskView(root);

        return root;
    }

    /**
     * Attaches task data, view, and display. Allowing for dynamically rendered tasks in the task display.
     * @param root
     */
    private void attachTaskView(View root){
        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView);//Grab display
        TaskAdapter taskAdapter = new TaskAdapter(taskManager, root);
        taskAdapter.setTasks(new ArrayList<>());
        taskDisplayView.setAdapter(taskAdapter);//attach display to view + tasks
        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        RecyclerView statsDisplayView = root.findViewById(R.id.stats_view);//Grab display
        StatsAdapter statsAdapter = new StatsAdapter(root);
        statsDisplayView.setAdapter(statsAdapter);//attach display to view
        statsDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        EventDispatch.registerListener(taskAdapter);
        taskManager.getTasksByPriority(new TaskConsumerStartup(taskAdapter));//Logic CALL--Load Tasks

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