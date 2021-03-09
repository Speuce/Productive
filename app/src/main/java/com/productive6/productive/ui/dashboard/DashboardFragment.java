package com.productive6.productive.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.objects.Task;

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
public class DashboardFragment extends Fragment {

    @Inject
    ITaskManager taskManager;

    private View popupView;

    private PopupWindow popupWindow;

    private DatePickerDialog datePickerDialog;

    private Calendar calendar;

    private View root;

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);


    /**
     * Creates parent view for the tasks
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

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

        EventDispatch.registerListener(taskAdapter);
        taskSorter.getTasksByPriority(new TaskConsumerStartup(taskAdapter));//Logic CALL--Load Tasks

        initPopupWindow();

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

    /**
     * Initializes the 'create task' popup window.
     * code from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
     *
     */
    protected void initPopupWindow(){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_task_popup, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setFocusable(true);

        popupView.setOnTouchListener((v, event) -> {
            InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            popupView.requestFocus();
            v.performClick();
            return false;
        });

        Button createButton = root.findViewById(R.id.newTaskButton);
        //Shows 'add task' popup on 'add' button press.
        createButton.setOnClickListener(v->{
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            dimBehind(popupWindow);
            initDatePicker();
            initPriority();
            initDifficulty();

            //On pressing the 'submit' button on the 'add task' popup, create new task object and submit it to the database.
            Button submitButton = popupView.findViewById(R.id.submit);
            submitButton.setOnClickListener(view->{
                //get priority choice
                RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
                RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                int priority = radioGroup.indexOfChild(radioButton);

                //get difficulty choice
                RadioGroup radioDiffGroup = popupView.findViewById(R.id.difficultyGroup);
                RadioButton radioDiffButton = radioDiffGroup.findViewById(radioDiffGroup.getCheckedRadioButtonId());
                int difficulty = radioDiffGroup.indexOfChild(radioDiffButton);

                EditText name = popupView.findViewById(R.id.taskNameForm);
                SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);
                //add task
                if (hasDeadline.isChecked()) {
                    taskManager.addTask(new Task(name.getText().toString(),priority+1,difficulty+1,0, calendar.getTime(),false));
                } else {
                    taskManager.addTask(new Task(name.getText().toString(),priority+1,difficulty+1,0, null,false));
                }

                popupWindow.dismiss();
                name.setText("");
            });
        });
    }

    /**
     * Dims the background behind a given popup window
     * @param popupWindow the popupwindow to dim around
     * Code from: https://stackoverflow.com/a/46711174/6047183
     */
    public void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    /**
     * Initializes the date picker window
     */
    private void initDatePicker() {
        Button dateButton = popupView.findViewById(R.id.datePickerButton);
        dateButton.setText(format.format(Calendar.getInstance().getTime()));
        SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);
        hasDeadline.setChecked(false);
        dateButton.setTextColor(Color.GRAY);
        dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        //Toggle the button if this task has due date
        hasDeadline.setOnClickListener(v -> {
            if (hasDeadline.isChecked()) {
                dateButton.setTextColor(Color.BLACK);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            } else {
                dateButton.setTextColor(Color.GRAY);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            }
        });

        //Set the calendar date to the date user selected
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            hasDeadline.setChecked(true);
            dateButton.setTextColor(Color.BLACK);
            dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            Date date = calendar.getTime();
            dateButton.setText(format.format(date));
        };

        //Initialize date to today when opening the date picker
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Initialize date picker dialog
        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //On clicking on date picker button on popup window, open dialog to choose date.
        dateButton.setOnClickListener(v->datePickerDialog.show());
    }

    /**
     * Initializes the priority default choice
     */
    private void initPriority() {
        RadioButton defaultChoice = popupView.findViewById(R.id.lowButton);
        defaultChoice.setChecked(true);

    }

    /**
     * Initializes the difficulty default choice
     */
    private void initDifficulty() {
        RadioButton defaultChoice = popupView.findViewById(R.id.easyButton);
        defaultChoice.setChecked(true);
    }
}