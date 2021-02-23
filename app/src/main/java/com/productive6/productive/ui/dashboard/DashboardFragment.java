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
import com.productive6.productive.logic.task.TaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.TaskPersistenceManager;
import com.productive6.productive.persistence.daos.TaskDao;
import com.productive6.productive.persistence.daos.TaskDao_Impl;
import com.productive6.productive.persistence.datamanage.PersistentDataManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends Fragment {

    @Inject
    TaskManager taskManager;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
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
        TaskAdapter taskAdapter = new TaskAdapter();

        taskManager.getTasksByPriority(new TaskConsumerStartup(taskAdapter));//DATABASE CALL--Load Tasks

        taskDisplayView.setAdapter(taskAdapter);//attach display to view + tasks
        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out
    }

    /**
     * Holds a callback for the database request made in attachTaskView.
     */
    public class TaskConsumerStartup implements Consumer<Collection<Task>> {
        private TaskAdapter taskAdapter;

        TaskConsumerStartup(TaskAdapter taskAdapter){this.taskAdapter = taskAdapter;}

        @Override
        public void accept(Collection<Task> tasks) {
            taskAdapter.setTasks((List<Task>) tasks);//Give data to view and automatically re-renders the view
        }
    }
}