package com.productive6.productive.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ITaskManager taskManager;
    @Inject
    ITitleManager titleManager;
    @Inject
    IUserManager userManager;

    private View popupView;

    private PopupWindow popupWindow;

    private DatePickerDialog datePickerDialog;

    private Calendar calendar;

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_rewards, R.id.navigation_todo, R.id.navigation_schedule)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        userManager.load();

        initPopupWindow();

    }

    /**
     * Initializes the 'create task' popup window.
     * code from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
     *
     */
    protected void initPopupWindow(){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_task_popup, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setFocusable(true);

        popupView.setOnTouchListener((v, event) -> {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                popupView.requestFocus();
                v.performClick();
                return false;
        });
    }

    /**
     * Shows 'add task' popup on 'add' button press.
     * @param view
     *
     */
    public void onButtonShowPopupWindowClick(View view) {

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);
        initDatePicker();
        initPriority();
        initDifficulty();

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
     * On pressing the 'submit' button on the 'add task' popup, create new task object and submit it to the database.
     * @param view
     */
    public void addTask(View view){
        EditText name = popupView.findViewById(R.id.taskNameForm);

        SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);

        RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
        RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        int priority = radioGroup.indexOfChild(radioButton);

        RadioGroup radioDiffGroup = popupView.findViewById(R.id.difficultyGroup);
        RadioButton radioDiffButton = radioDiffGroup.findViewById(radioDiffGroup.getCheckedRadioButtonId());
        int difficulty = radioDiffGroup.indexOfChild(radioDiffButton);

        if (hasDeadline.isChecked()) {
            taskManager.addTask(new Task(name.getText().toString(),priority+1,difficulty+1,0, calendar.getTime(),false));
        } else {
            taskManager.addTask(new Task(name.getText().toString(),priority+1,difficulty+1,0, null,false));
        }

        popupWindow.dismiss();
        name.setText("");
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

        int style = R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Spinner;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
    }

    /**
     * On clicking on date picker button on popup window, open dialog to choose date.
     * @param view
     */
    public void deadlinePicker(View view)
    {
        datePickerDialog.show();
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