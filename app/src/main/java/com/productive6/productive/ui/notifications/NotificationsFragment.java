package com.productive6.productive.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.productive6.productive.R;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.ui.dashboard.TaskAdapter;

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

    /** Views */
    private TextView monthTextView;
    private TextView userClickedDate;
    private RecyclerView taskDisplayView;
    private Calendar calendar;
    private CompactCalendarView calendarView;

    /** Variables */
    private String dateInSDF;
    private String month;
    private List<Task> tasksByDate;
    private SimpleDateFormat sdf;

    @Inject
    ITaskManager taskManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        initOnCreate(root);
        return root;
    }

    private void initOnCreate(View root) {
        /** Initialize views */
        taskDisplayView = root.findViewById(R.id.taskDisplayView);
        userClickedDate = root.findViewById(R.id.selectedDateTextView);
        monthTextView   = root.findViewById(R.id.monthNameTextView);
        calendarView    = root.findViewById(R.id.calendarView);

        /** Initialize variables */
        calendar    = (Calendar.getInstance());
        sdf         = new SimpleDateFormat("yyyy-M-d");
        dateInSDF   = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        month       = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        tasksByDate = new ArrayList<>();

        /** Set date and time */
        calendar.setTime(new Date());
        monthTextView.setText(month + " " + (calendar.get(Calendar.YEAR)));
        userClickedDate.setText("Tasks scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);

        /** Initialize tasks for the RecyclerView */
        initTaskList(dateInSDF, root, sdf);

        /** Set a listener to CompactCalendarView */
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                calendar.setTime(dateClicked);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                dateInSDF = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
                initTaskList(dateInSDF, root, sdf); // refresh task list upon click
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendar.setTime(firstDayOfNewMonth);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                monthTextView.setText(month + " " + calendar.get(Calendar.YEAR));
            }
        });
    }

    private String getDateWithSuffix(int day) {
        switch (day % 10) {
            case 1:  return day + "st";
            case 2:  return day + "nd";
            case 3:  return day + "rd";
            default: return day + "th";
        }
    }

    private void initTaskList(String selectedDate, View root, SimpleDateFormat sdf) {
        TaskAdapter taskAdapter = new TaskAdapter(taskManager, root);
        taskDisplayView.setAdapter(taskAdapter);
        taskDisplayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        taskManager.getTasksByPriority(new NotificationsFragment.TaskConsumerStartup(taskAdapter, sdf, selectedDate));//Logic CALL--Load Tasks
    }

    /** Holds a callback for the database request made in attachTaskView */
    public class TaskConsumerStartup implements Consumer<List<Task>> {
        private final TaskAdapter taskAdapter;
        private final SimpleDateFormat sdf;
        private String selectedDate;

        TaskConsumerStartup(TaskAdapter taskAdapter, SimpleDateFormat sdf, String selectedDate) {
            this.taskAdapter = taskAdapter;
            this.sdf = sdf;
            this.selectedDate = selectedDate;
        }

        @Override
        public void accept(List<Task> tasks) {
            /** Clear the List, repopulate only with the tasks on the clicked date */

            tasksByDate.clear();

            for (Task task : tasks) {
                if (task.getDueDate() != null) {
                    dateInSDF = sdf.format(task.getDueDate());
                    if (dateInSDF.equals(selectedDate)) {
                        tasksByDate.add(task);
                    }
                }
                else tasksByDate.add(task);
            }

            taskAdapter.setTasks(tasksByDate);

            /** Set text above the RecyclerView based on the number of task on the clicked date */
            userClickedDate.setText(tasksByDate.size() + " task(s) scheduled for " + getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        }
    }
}