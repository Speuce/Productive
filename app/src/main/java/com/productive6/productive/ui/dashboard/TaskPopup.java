package com.productive6.productive.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.productive6.productive.R;
import com.productive6.productive.logic.util.CalenderUtilities;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.enums.Category;
import com.productive6.productive.objects.enums.Difficulty;
import com.productive6.productive.objects.enums.Priority;

import java.time.LocalDate;
import java.util.function.Consumer;

import javax.annotation.Nullable;

public class TaskPopup {

    private View popupView;
    private PopupWindow popupWindow;
    private DatePickerDialog datePickerDialog;
    private final Context context;
    private Spinner categorySpinner;
    private int spinnerClickedId;

    /**
     * The task being changed.
     */
    private final Task task;

    private final Consumer<Task> complete;

    public TaskPopup(Context context, @Nullable Task t, Consumer<Task> completeCallback) {
        this.context = context;
        this.complete = completeCallback;
        if (t == null) {
            this.task = new Task();
        } else {
            this.task = t;
        }
        initPopupWindow();
    }

    /**
     * Initializes the 'edit/add task' popup window.
     * code from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
     */
    protected void initPopupWindow() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_task_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setFocusable(true);

        popupView.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            popupView.requestFocus();
            v.performClick();
            return false;
        });
    }

    /**
     * Opens this popup window and displays it to the user
     *
     * @param view the view to display the popup over.
     */
    public void open(View view) {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

        //Fill in all fields (if applicable)
        initDatePicker();
        initPriority();
        initDifficulty();
        initCategory();
        initName();

        EditText name = popupView.findViewById(R.id.taskNameForm);
        name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        //Submit button to update info
        Button submit = popupView.findViewById(R.id.submit);
        submit.setOnClickListener(onSubmit());
    }

    /**
     * Inner code is called when the 'submit' button is clicked in the UI
     * This method pulls the fields off of the checkboxes and such, updates the task
     * at hand, and calls the complete consumer.
     */
    private View.OnClickListener onSubmit() {
        return v -> {
            //update task name
            EditText name = popupView.findViewById(R.id.taskNameForm);
            task.setTaskName(name.getText().toString());

            //update priority choice
            RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
            RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            int priority = radioGroup.indexOfChild(radioButton);
            task.setPriority(Priority.values()[priority]);

            //update difficulty choice
            RadioGroup radioDiffGroup = popupView.findViewById(R.id.difficultyGroup);
            RadioButton radioDiffButton = radioDiffGroup.findViewById(radioDiffGroup.getCheckedRadioButtonId());
            int difficulty = radioDiffGroup.indexOfChild(radioDiffButton);
            task.setDifficulty(Difficulty.values()[difficulty]);

            //update category choice
            task.setCategory(Category.values()[spinnerClickedId]);

            //update due date choice
            SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);
            if (!hasDeadline.isChecked()) {
                task.setDueDate(null);
            }

            popupWindow.dismiss();
            complete.accept(task);
        };
    }

    /**
     * Dims the background behind a given popup window
     *
     * @param popupWindow the popupwindow to dim around
     *                    Code from: https://stackoverflow.com/a/46711174/6047183
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
        SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);
        Button dateButton = popupView.findViewById(R.id.datePickerButton);
        //Initialize date to today when opening the date picker
        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now());
            hasDeadline.setChecked(false);
            dateButton.setTextColor(Color.GRAY);
            dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        } else {
            hasDeadline.setChecked(true);
            dateButton.setTextColor(ContextCompat.getColor(context, R.color.smoke_white));
            dateButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.smoke_white)));
        }

        dateButton.setText(CalenderUtilities.DATE_FORMATTER.format(task.getDueDate()));

        //Toggle the button if this task has due date
        hasDeadline.setOnClickListener(v -> {
            if (hasDeadline.isChecked()) {
                dateButton.setTextColor(ContextCompat.getColor(context, R.color.smoke_white));
                dateButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.smoke_white)));
            } else {
                dateButton.setTextColor(Color.GRAY);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            }
        });

        //Set the calendar date to the date user selected
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            hasDeadline.setChecked(true);
            dateButton.setTextColor(ContextCompat.getColor(context, R.color.smoke_white));
            dateButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.smoke_white)));
            task.setDueDate(LocalDate.of(year, month + 1, dayOfMonth));
            dateButton.setText(CalenderUtilities.DATE_FORMATTER.format(task.getDueDate()));
        };


        //Initialize date picker dialog
        datePickerDialog = new DatePickerDialog(context, dateSetListener, task.getDueDate().getYear(), task.getDueDate().getMonthValue() - 1, task.getDueDate().getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //On clicking on date picker button on popup window, open dialog to choose date.
        dateButton.setOnClickListener(v -> datePickerDialog.show());
    }

    /**
     * Initializes the "name" field of the popup
     */
    private void initName() {
        EditText name = popupView.findViewById(R.id.taskNameForm);
        name.setText(task.getTaskName());
    }

    /**
     * Get priority of task
     */
    private void initPriority() {
        RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
        RadioButton oldChoice = (RadioButton) radioGroup.getChildAt(task.getPriority().ordinal());
        oldChoice.setChecked(true);

    }

    /**
     * x
     * Get difficulty of task
     */
    private void initDifficulty() {
        RadioGroup radioGroup = popupView.findViewById(R.id.difficultyGroup);
        RadioButton oldChoice = (RadioButton) radioGroup.getChildAt(task.getDifficulty().ordinal());
        oldChoice.setChecked(true);
    }

    private void initCategory() {
        categorySpinner = popupView.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        //listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerClickedId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
