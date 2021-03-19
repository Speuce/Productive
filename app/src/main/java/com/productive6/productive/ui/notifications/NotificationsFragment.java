package com.productive6.productive.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IStreakRewardManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.logic.util.CalenderUtilities;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskUpdateEvent;
import com.productive6.productive.ui.dashboard.TaskAdapter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationsFragment extends Fragment implements ProductiveListener {

    /** Views */
    private TextView monthTextView;
    private TextView userClickedDate;
    private RecyclerView taskDisplayView;
    private Calendar calendar;
    private CompactCalendarView calendarView;

    /** Variables */
    private LocalDate dateInSDF;
    private String month;
    private List<Task> tasksByDate;
    private DateTimeFormatter sdf;

    private View root;

    @Inject
    ITaskManager taskManager;

    @Inject
    ITaskSorter taskSorter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        initOnCreate(root);
        return root;
    }

    private void initOnCreate(View root) {
        EventDispatch.registerListener(this);
        this.root = root;
        /** Initialize views */
        taskDisplayView = root.findViewById(R.id.taskDisplayView);
        userClickedDate = root.findViewById(R.id.selectedDateTextView);
        monthTextView   = root.findViewById(R.id.monthNameTextView);
        calendarView    = root.findViewById(R.id.calendarView);


        /** Initialize variables */
        calendar    = (Calendar.getInstance());
        sdf         = CalenderUtilities.DATE_FORMATTER;
        dateInSDF   = LocalDate.now();
        month       = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        tasksByDate = new ArrayList<>();

        /** Set date and time */
        calendar.setTime(new Date());
        fillMonth(LocalDate.now().withDayOfMonth(1));
        monthTextView.setText(month + " " + (calendar.get(Calendar.YEAR)));

        /** Initialize tasks for the RecyclerView */
        initTaskList(dateInSDF, root);


        /** Set a listener to CompactCalendarView */
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                calendar.setTime(dateClicked);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                dateInSDF = dateClicked.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                initTaskList(dateInSDF, root); // refresh task list upon click
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                fillMonth(firstDayOfNewMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                calendar.setTime(firstDayOfNewMonth);
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                monthTextView.setText(month + " " + calendar.get(Calendar.YEAR));

            }
        });
    }

    /**
     * Calls the logic layer and asks it which days on the given month have a task assigned.
     * Our job is to add the little dot things.
     */
    private void fillMonth(LocalDate first){
        calendarView.removeAllEvents();
        taskSorter.getDaysWithTaskInMonth(first, (day) ->{
            calendarView.addEvent(new Event(ContextCompat.getColor(getContext(),R.color.pastel_red), day.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000L));
        });
    }


    private void initTaskList(LocalDate date, View root) {
        TaskAdapter taskAdapter = new TaskAdapter(taskManager, root);
        taskDisplayView.setAdapter(taskAdapter);
        taskDisplayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        taskSorter.getTasksOnDate(date, (tasks -> {

            tasksByDate.clear();
            dateInSDF = date;
            tasksByDate.addAll(tasks);

            taskAdapter.setTasks(tasksByDate);

            /** Set text above the RecyclerView based on the number of task on the clicked date */
            userClickedDate.setText(tasksByDate.size() + " task(s) scheduled for " + CalenderUtilities.getDateWithSuffix(calendar.get(Calendar.DATE)) + " " + month);
        }));
    }

    @ProductiveEventHandler
    public void onUpdate(TaskUpdateEvent e){
        initTaskList(dateInSDF, root);
        fillMonth(dateInSDF.withDayOfMonth(1));
    }

}