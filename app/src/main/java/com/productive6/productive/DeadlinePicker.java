package com.productive6.productive;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DeadlinePicker extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private SwitchCompat hasDeadline;
    private final String pattern = "MMM dd, yyyy";
    private final SimpleDateFormat formatDate = new SimpleDateFormat(pattern, Locale.CANADA);
    private Date selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.date_picker);*/
        initDatePicker();
    }

    private void initDatePicker() {
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(formatDate.format(Calendar.getInstance().getTime()));
        hasDeadline = findViewById(R.id.switchDeadline);
        dateButton.setTextColor(Color.GRAY);
        dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        hasDeadline.setOnClickListener(v -> {
            if (hasDeadline.isChecked()) {
                dateButton.setTextColor(Color.BLACK);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            } else {
                dateButton.setTextColor(Color.GRAY);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            }
        });

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            hasDeadline.setChecked(true);
            dateButton.setTextColor(Color.BLACK);
            dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            month = month + 1;
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            Date date = calendar.getTime();
            dateButton.setText(formatDate.format(date));
            selectDate = date;
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Spinner;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
    }

    public void deadlinePicker(View view)
    {
        datePickerDialog.show();
    }
}
