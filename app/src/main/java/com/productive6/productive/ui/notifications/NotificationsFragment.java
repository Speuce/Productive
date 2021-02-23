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
import com.productive6.productive.objects.Task;
import com.productive6.productive.ui.dashboard.TaskAdapter;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationsFragment extends Fragment {
    private TextView showMonthName;
    private TextView clickedDate;
    private String dateInString;
    private String month;
    private RecyclerView taskDisplayView;
    private ArrayList<Task> tasks;
    private ArrayList<Task> filteredByDateTasks;

    private Calendar calendar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        taskDisplayView = root.findViewById(R.id.taskDisplayView); //Grab display
        CompactCalendarView calendarView = root.findViewById(R.id.calendarView);
        clickedDate = root.findViewById(R.id.selectedDateTextView);
        showMonthName = root.findViewById(R.id.monthNameTextView);
        showMonthName.setText(month + " " + (calendar.get(Calendar.YEAR)));
        clickedDate.setText("Tasks scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        filteredByDateTasks = new ArrayList<>();
        tasks = new ArrayList<>();

        tasks.add(new Task("taskName1000", 1, 1, 1, "2021-2-24", false));
        tasks.add(new Task("taskName2321", 1, 1, 1, "2021-2-24", false));
        tasks.add(new Task("taskName9999", 1, 1, 1, "2021-2-24", false));
        tasks.add(new Task("taskName2879", 1, 1, 1, "2021-2-25", false));

        calendarView.addEvent(new Event(Color.BLUE, 1614146400000L, tasks.get(2).getTaskName())); //24 Feb 2021
        calendarView.addEvent(new Event(Color.BLUE, 1614232800000L, tasks.get(2).getTaskName())); //25 Feb 2021

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                calendar.setTime(dateClicked);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                dateInString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
                clickedDate.setText("Tasks scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
                initializeTaskList(dateInString);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendar.setTime(firstDayOfNewMonth);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                showMonthName.setText(month + " " + calendar.get(Calendar.YEAR));
            }
        });

        initializeTaskList(dateInString);
        return root;
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
    private void initializeTaskList(String selectedDate) {
        filteredByDateTasks.clear();

        for (Task task : tasks) {
            if (task.getDueDate().equals(selectedDate)) {
                filteredByDateTasks.add(task);
            }
        }

        if (filteredByDateTasks.size() == 0) {
            clickedDate.setText("No tasks on " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        }

        TaskAdapter taskAdapter = new TaskAdapter();
        taskAdapter.setTasks(filteredByDateTasks);
        taskDisplayView.setAdapter(taskAdapter);
        taskDisplayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}