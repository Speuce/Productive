package com.productive6.productive.ui.stats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.productive6.productive.R;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.ui.dashboard.StatsAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity {

    @Inject
    ITaskStatsManager statsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        BarChart barChart = findViewById(R.id.bar_chart);

        ArrayList<BarEntry> days = new ArrayList<>();

        BarDataSet barDataSet = new BarDataSet(days, "Days");
        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.setFitBars(true);
        barChart.getDescription().setText("Bar Chart");
        barChart.animateY(500);


        RecyclerView statsDisplayView = findViewById(R.id.stats_view);//Grab display
        StatsAdapter statsAdapter = new StatsAdapter();
        statsDisplayView.setAdapter(statsAdapter);//attach display to view
        statsDisplayView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));//Describe how the data should be laid out

        statsManager.getTasksCompletedPastDays(7,(dayIntTuple)->{
            barDataSet.addEntry(new BarEntry(dayIntTuple.getDate().getDayOfMonth(),dayIntTuple.getNumber()));

            barChart.setData(barData);

        });
    }
}