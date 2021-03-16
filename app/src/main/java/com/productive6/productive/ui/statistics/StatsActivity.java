package com.productive6.productive.ui.statistics;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.productive6.productive.R;
import com.productive6.productive.persistence.Converters;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity {

    /**
     * The current dataset
     */
    private LineDataSet dataSet;

    /**
     * The current chart.
     */
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        chart = findViewById(R.id.chart);

        dataSet = new LineDataSet(new ArrayList<>(), "");

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(tfLight);
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

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
//        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //leftAxis.setAxisLineDashedLine(new DashPathEffect(new float[]{10, 20}, 5));

        chart.setDescription(null);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        addData(LocalDate.now(), 50);
        addData(LocalDate.now().plusDays(1), 10);
        addData(LocalDate.now().plusDays(2), 0);
        addData(LocalDate.now().plusDays(3), 40);
        addData(LocalDate.now().plusDays(4), 26);
        refreshData();



    }

    /**
     * Clears the dataset
     */
    public void clearData(){
        dataSet.clear();
    }

    public void addData(LocalDate date, int value){
        dataSet.addEntry(new Entry(date.toEpochDay(), value));
    }

    /**
     * Refreshes the dataset on the graph
     */
    private void refreshData(){
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(ColorTemplate.getHoloBlue());
        dataSet.setValueTextColor(ColorTemplate.getHoloBlue());
        dataSet.setLineWidth(1.5f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(ColorTemplate.getHoloBlue());
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(dataSet);
        data.setValueTextColor(Color.RED);
        data.setValueTextSize(9f);

        // set data
        chart.setData(data);

        Legend l = chart.getLegend();
        l.setEnabled(false);
    }



    private void setData(LineChart chart){
        // now in hours
        long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<>();


        // increment by 1 hour
        for (float x = days; x < days+5; x++) {

            float y = (float) (Math.random()*50);
            values.add(new Entry(x, y)); // add one entry per day
        }

        // create a dataset and give it a type
       
    }




}