package com.productive6.productive.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private LocalDate selectedDate;

    /**
     * For formatting dates in the view
     */
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    }


        SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);

        RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
        RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        int priority = radioGroup.indexOfChild(radioButton);

        RadioGroup radioDiffGroup = popupView.findViewById(R.id.difficultyGroup);
        RadioButton radioDiffButton = radioDiffGroup.findViewById(radioDiffGroup.getCheckedRadioButtonId());
        int difficulty = radioDiffGroup.indexOfChild(radioDiffButton);

        if (hasDeadline.isChecked()) {
            taskManager.addTask(new Task(name.getText().toString(),priority+1,difficulty+1,0, selectedDate,false));
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

        //Initialize date to today when opening the date picker
        selectedDate = LocalDate.now();

        Button dateButton = popupView.findViewById(R.id.datePickerButton);
        dateButton.setText(format.format(selectedDate));
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
            selectedDate = LocalDate.of(year, month+1, dayOfMonth);
            dateButton.setText(format.format(selectedDate));
        };



        //Initialize date picker dialog
        datePickerDialog = new DatePickerDialog(this, dateSetListener, selectedDate.getYear(),
                selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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