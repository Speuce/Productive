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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    //private NotificationsViewModel notificationsViewModel;
    private TextView clickedDate;
    //    private CalendarView calendarView;
    private CompactCalendarView calendarView;
    private String today;
    private String dateInString;

    private static final String TAG = "NotificationsFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        clickedDate = view.findViewById(R.id.selectedDateTextView);
        today = new SimpleDateFormat("dd/M/yyyy").format(new Date());
        clickedDate.setText("Tasks scheduled for " + today);
//        dateInString = new SimpleDateFormat("yyyy-M-dd").format(new Date());
        dateInString = new SimpleDateFormat("EEE MMM d").format(new Date()) + "00:00:00 CST 2021";

        /*
        // used for the default calendarView
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendarView.setFocusedByDefault(false);
            dateInString = year + "-" + (month + 1) + "-" + dayOfMonth;
            clickedDate.setText("Tasks scheduled for " + dayOfMonth + " " + intToMonth(month) + ", " + year);
            initializeTaskList(view, dateInString);
        });
        */

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
//                Toast.makeText(getContext(), dateClicked.toString(), Toast.LENGTH_SHORT).show();
//                if (dateClicked.toString().compareTo(dateInString) == 0) {
//                    //dateClicked.toString() == ("Fri Oct 21 00:00:00 AST 2016")
//                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateClicked);

                int day = calendar.get(Calendar.DATE);
                int month = (calendar.get(Calendar.MONTH)+1);
                int year = calendar.get(Calendar.YEAR);

                dateInString = year + "-" + month + "-" + day;
//                Toast.makeText(getContext(), dateInString, Toast.LENGTH_SHORT).show();
//                dateInString = new SimpleDateFormat("EEE MMM d").format(new Date()) + " 00:00:00 CST 2021";
                initializeTaskList(view, dateInString);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Toast.makeText(getContext(), new SimpleDateFormat("MMMM yyyy").format(firstDayOfNewMonth), Toast.LENGTH_SHORT).show();
                // TODO Show the month when scrolling to a different month
            }
        });

        initializeTaskList(view, dateInString);
        return view;
    }

    // TODO Fix this buggy function
//    private String dateToEpoch(String dueDate) {
//        Date date = null;
//        long epoch = 0;
//        String temp = dueDate;
//
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d");
//            date = simpleDateFormat.parse(temp + "00:00:00 CST 2021");
//            epoch = date.getTime();
//            System.out.println(epoch);
//        } catch (ParseException parseException) {
//            Log.d(TAG, "initializeTaskList: Date couldn't be parsed");
//        }
//        Toast.makeText(getContext(), (int) epoch, Toast.LENGTH_SHORT).show();
//        return (epoch + "L");
//    }

    // Modified version of Luke's code from the dashboard fragment
    private void initializeTaskList(View root, String selectedDate) {

        RecyclerView taskDisplayView = root.findViewById(R.id.taskDisplayView); //Grab display

        ArrayList<Task> tasks = new ArrayList<>(); //Data
        tasks.add(new Task("taskName1", 1, 1, 1, "2021-2-24", false));
        tasks.add(new Task("taskName2", 1, 1, 1, "2021-2-25", false));
        tasks.add(new Task("taskName3", 1, 1, 1, "Wed Feb 24 00:00:00 CST 2021", false));
        tasks.add(new Task("taskName4", 1, 1, 1, "Wed Feb 24 00:00:00 CST 2021", false));

        ArrayList<Task> filteredByDateTasks = new ArrayList<>();
        calendarView.addEvent(new Event(Color.BLUE, 1614146400000L, tasks.get(2).getTaskName()));
        calendarView.addEvent(new Event(Color.BLUE, 1613368800000L, tasks.get(2).getTaskName()));
        calendarView.addEvent(new Event(Color.BLUE, 1612245600000L, tasks.get(2).getTaskName()));
//        calendarView.addEvent(new Event(Color.BLUE, 1613368800000L, tasks.get(2).getTaskName()));
        for (Task task : tasks) {
//            calendarView.addEvent(new Event(Color.RED, Long.parseLong(dateToEpoch(task.getDueDate())), task.getTaskName()));

            if (task.getDueDate().equals(selectedDate)) {
                filteredByDateTasks.add(task);
            }
        }

        TaskAdapter taskAdapter = new TaskAdapter();
        taskAdapter.setTasks(filteredByDateTasks); //Give data to view
        taskDisplayView.setAdapter(taskAdapter); //attach display to view + data
        taskDisplayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)); //Describe how the data should be laid out
    }
}