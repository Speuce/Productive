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

import java.util.ArrayList;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

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

        ArrayList<Task> tasks = new ArrayList<>();//Data
        tasks.add(new Task("String taskName1", 1, 1, 1, new Date(), false));
//        tasks.add(new Task("String taskName2", 2, 2, false));
//        tasks.add(new Task("String taskName3", 3, 3, false));
//        tasks.add(new Task("String taskName4", 4, 4, false));
//        tasks.add(new Task("String taskName5", 5, 5, false));
//        tasks.add(new Task("String taskName1", 1, 1, false));
//        tasks.add(new Task("String taskName2", 2, 2, false));
//        tasks.add(new Task("String taskName3", 3, 3, false));
//        tasks.add(new Task("String taskName4", 4, 4, false));
//        tasks.add(new Task("String taskName5", 5, 5, false));

        TaskAdapter taskAdapter = new TaskAdapter();
        taskAdapter.setTasks(tasks);//Give data to view

        taskDisplayView.setAdapter(taskAdapter);//attach display to view + data
        taskDisplayView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out
    }
}