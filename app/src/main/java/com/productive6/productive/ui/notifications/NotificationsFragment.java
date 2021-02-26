package com.productive6.productive.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.productive6.productive.MainActivity;
import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.ui.dashboard.TaskAdapter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationsFragment extends Fragment {
    private static final String TAG = "NotificationsFragment";

    // Views
    private TextView showMonthName;
    private TextView clickedDate;
    private RecyclerView taskDisplayView;
    private Calendar calendar;
    private CompactCalendarView calendarView;
    @Inject
    ITaskManager taskManager;

    // Variables
    private String dateInString;
    private String month;
    private ArrayList<Task> tasks;
    private ArrayList<Task> filteredByDateTasks;
    private int yearOffset = 1900; // Not sure why but passing the year as parameter starts from 1900, so 2021 is 121 (2021-1900)


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        initViews(root);

        return root;
    }

    private void initViews(View root) {
        calendar = (Calendar.getInstance());
        calendar.setTime(new Date());
        month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        taskDisplayView = root.findViewById(R.id.taskDisplayView); //Grab display
        clickedDate = root.findViewById(R.id.selectedDateTextView);
        showMonthName = root.findViewById(R.id.monthNameTextView);
        showMonthName.setText(month + " " + (calendar.get(Calendar.YEAR)));
        clickedDate.setText("Tasks scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        filteredByDateTasks = new ArrayList<>();
        dateInString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);

        initTasksArrayList(); // initializing tasks for the RecyclerView
        initCompactCalendarView(root); // initializing tasks for the CompactCalendarView
        initializeTaskList(dateInString, root);
    }

    private void initTasksArrayList() {
        tasks = new ArrayList<>();
        tasks.add(new Task("Buy groceries", 1, 1, 1, new Date(2021 - yearOffset, 1, 24), false));
        tasks.add(new Task("Finish assignment", 1, 1, 1, new Date(2021 - yearOffset, 1, 25), false));
        tasks.add(new Task("Clean the house", 1, 1, 1, new Date(2021 - yearOffset, 1, 25), false));
        tasks.add(new Task("Submit assignment", 1, 1, 1, new Date(2021 - yearOffset, 1, 25), false));
    }

    private void initCompactCalendarView(View root) {
        calendarView = root.findViewById(R.id.calendarView);
        calendarView.addEvent(new Event(Color.BLUE, 1614146400000L, null)); //24 Feb 2021
        calendarView.addEvent(new Event(Color.BLUE, 1614232800000L, null)); //25 Feb 2021

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                calendar.setTime(dateClicked);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                dateInString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
                initializeTaskList(dateInString, root);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendar.setTime(firstDayOfNewMonth);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                showMonthName.setText(month + " " + calendar.get(Calendar.YEAR));
            }
        });
    }

    private String getDateWithSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return day + "st";
            case 2:
                return day + "nd";
            case 3:
                return day + "rd";
            default:
                return day + "th";
        }
    }

    // Modified from Luke's code in the dashboard fragment
    private void initializeTaskList(String selectedDate, View root) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        filteredByDateTasks.clear();

        for (Task task : tasks) {
            dateInString = sdf.format(task.getDueDate());
            if (dateInString.equals(selectedDate)) {
                filteredByDateTasks.add(task);
            }
        }

        if (filteredByDateTasks.size() == 0) {
            clickedDate.setText("No tasks on " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        } else if (filteredByDateTasks.size() == 1) {
            clickedDate.setText(filteredByDateTasks.size() + " task scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        } else {
            clickedDate.setText(filteredByDateTasks.size() + " tasks scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        }

        TaskAdapter taskAdapter = new TaskAdapter(taskManager, getContext(), root);
        taskAdapter.setTasks(filteredByDateTasks);
        taskDisplayView.setAdapter(taskAdapter);
        taskDisplayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}