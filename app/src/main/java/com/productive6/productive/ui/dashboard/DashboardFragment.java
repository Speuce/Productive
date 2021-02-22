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
import com.productive6.productive.objects.Task;
import com.productive6.productive.persistence.TaskPersistenceManager;
import com.productive6.productive.persistence.daos.TaskDao;
import com.productive6.productive.persistence.daos.TaskDao_Impl;
import com.productive6.productive.persistence.datamanage.PersistentDataManager;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initializeTaskList(root);

        return root;
    }

    /**
     * Populates the task list in the dashboard fragment.
     * @param root
     */
    private void initializeTaskList(View root){

        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView);//Grab display

        List<Task> tasks = fetchTasks(root);//Fetch data from backend

        TaskAdapter taskAdapter = new TaskAdapter();
        taskAdapter.setTasks(tasks);//Give data to view

        taskDisplayView.setAdapter(taskAdapter);//attach display to view + data
        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out
    }

    /**
     * Fetch tasks from backend and return
     * @return
     */
    private List<Task> fetchTasks(View root){
        TaskPersistenceManager taskPersistenceManager = new PersistentDataManager(root.getContext()).task();
        List<Task> tasks = taskPersistenceManager.getAllTasks();//get all tasks from backend

        return tasks;
    };
}