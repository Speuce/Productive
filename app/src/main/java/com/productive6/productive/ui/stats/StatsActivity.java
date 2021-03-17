package com.productive6.productive.ui.stats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.productive6.productive.R;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.ui.dashboard.StatsAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Inject
    ITaskStatsManager statsManager;
    BarChart barChart;
    final int DEFAULT_HISTORY = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        barChart = findViewById(R.id.bar_chart);

        Spinner sort_by = (Spinner) findViewById(R.id.dateRangeSelection);
        sort_by.setOnItemSelectedListener(this);

        ArrayList<BarEntry> days = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(days, "Days");

        RecyclerView statsDisplayView = findViewById(R.id.stats_view);//Grab display
        StatsAdapter statsAdapter = new StatsAdapter();
        statsDisplayView.setAdapter(statsAdapter);//attach display to view
        statsDisplayView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        buildGraph( DEFAULT_HISTORY);
    }

    private void buildGraph( int history){

        ArrayList<BarEntry> days = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(days, "Days");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int)value + "";
            }
        });


        barChart.getAxisRight().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter(new ValueFormatter() {

            private final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd ''yy");
            @Override
            public String getFormattedValue(float value) {
                LocalDate day = LocalDate.ofEpochDay((long) value);
                return format.format(day);
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        statsManager.getTasksCompletedPastDays(history,(dayIntTuple)->{
            barDataSet.addEntry(new BarEntry(dayIntTuple.getDate().toEpochDay(),dayIntTuple.getNumber()));

            BarData barData = new BarData(barDataSet);

            barDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "";
                }
            });
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(16f);
            barChart.setFitBars(true);
            barChart.getDescription().setText("");
            barChart.animateY(500);
            barChart.setData(barData);
            barChart.invalidate();
        });

    }

    /**
     * Reacts to changes made to the sort-by dropdown.
     * Uses a switch statement to perform different actions based on what the dropdown was changed to.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        switch(selection){
            case "7 days":
                buildGraph( 7);
                break;
            case "14 days":
                buildGraph( 14);
                break;
            case "30 days":
                buildGraph( 30);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;//Do nothing when nothing is selected
    }
}