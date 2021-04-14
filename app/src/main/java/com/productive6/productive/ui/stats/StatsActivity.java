package com.productive6.productive.ui.stats;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.productive6.productive.R;
import com.productive6.productive.logic.statstics.ICoinsStatsManager;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.statstics.IXPStatsManager;
import com.productive6.productive.logic.util.CalenderUtilities;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Inject
    IXPStatsManager xpStatsManager;

    @Inject
    ICoinsStatsManager coinsStatsManager;

    @Inject
    ITaskStatsManager statsManager;

    /**
     * The Task Completed Chart
     */
    private BarChart barChart;

    /**
     * The default number of days to go back (if the user hasn't selected yet)
     */
    private static final int DEFAULT_HISTORY = 7;

    /**
     * For displaying the user's various statistics
     */
    private StatsAdapter statsAdapter;

    private Timer timer;

    private static String sortingBySelection;
    private static int sortBySelectionInt;

    /**
     * Mapping of String display to DateSelection objects.
     */
    private Map<String, DateSelection> dateSelectionMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        dateSelectionMap = Arrays.stream(DateSelection.values()).collect(Collectors.toMap(DateSelection::getDisplay, i -> i));


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->onBackPressed());

        barChart = findViewById(R.id.bar_chart);
        barChart.getAxisRight().setEnabled(false);
        configureXAxis();
        configureYAxis();

        Spinner sort_by = (Spinner) findViewById(R.id.dateRangeSelection);

        List<String> itemsList = Arrays.stream(DateSelection.values()).map(DateSelection::getDisplay).collect(Collectors.toList());

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, itemsList);
        stringArrayAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        sort_by.setAdapter(stringArrayAdapter);

        if(sortingBySelection !=null) {//Set the spinner to where it was left the last time the user was on the page.
            sort_by.setSelection(sortBySelectionInt);
        }
        sort_by.setOnItemSelectedListener(this);


        RecyclerView statsDisplayView = findViewById(R.id.stats_view);//Grab display
        statsAdapter = new StatsAdapter();
        statsDisplayView.setAdapter(statsAdapter);//attach display to view
        statsDisplayView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NotNull RecyclerView rv, @NotNull MotionEvent e) {
                return true;
            }
        });

        setLayoutManager();
        fillStatistics();
        setTimer();



        //Describe how the data should be laid out

        buildGraph( DEFAULT_HISTORY);
    }

    /**
     * Custom code to make the statistics scroll slower.
     */
    private void setLayoutManager(){
        RecyclerView statsDisplayView = findViewById(R.id.stats_view);//Grab display
        //this code is for the smooth scrolling affect of the recylerview
        //code from https://stackoverflow.com/a/36784136/6047183
        statsDisplayView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {


                final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return super.computeScrollVectorForPosition(targetPosition);
                    }

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return 200f / displayMetrics.densityDpi;
                    }
                };
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        });
    }



    /**
     * Create the timer for the autoscroller
     */
    private void setTimer(){
        RecyclerView statsDisplayView = findViewById(R.id.stats_view);//Grab display
        //and this is for the autoscrolling part
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            final AtomicInteger i = new AtomicInteger(0);
            @Override
            public void run() {
                if(statsAdapter.getItemCount() == 0){
                    return;
                }
                if(i.incrementAndGet() >= statsAdapter.getItemCount()){
                    i.set(0);
                }
                statsDisplayView.smoothScrollToPosition(i.get());
            }
        }, 3000L, 2500L);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    /**
     * Gets the Statistics from StatsManagers and fills in the ui with them
     */
    private void fillStatistics(){
        statsManager.getAverageTasksCompletedDaily(val ->{
            statsAdapter.setStat("Average Tasks Per Day", ""+(Math.round(val * 100.0) / 100.0));
        });
        statsManager.getTasksCompletedAllTime(val ->{
            statsAdapter.setStat("Tasks Completed All Time", "" + val);
        });
        coinsStatsManager.getCoinsEarnedAllTime(val ->{
            statsAdapter.setStat("Coins Earned All Time", "" + val);
        });
        xpStatsManager.getXPEarnedAllTime(val ->{
            statsAdapter.setStat("Xp Earned All Time", "" + val);
        });
        statsManager.getFirstTaskDay(val -> {
            statsAdapter.setStat("Tasker Since", CalenderUtilities.DATE_FORMATTER.format(val));
        });
    }

    private void configureYAxis(){
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
    }

    /**
     * Configures the x axis of the graph.
     */
    private void configureXAxis(){

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
    }

    /**
     * Builds the graph going back x days
     * @param history x -- the number of days to go back in history
     */
    private void buildGraph( int history){
        ArrayList<BarEntry> days = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(days, "Days");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int)value + "";
            }
        });

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(Math.min(history, 10));

        YAxis leftAxis = barChart.getAxisLeft();
        AtomicInteger max = new AtomicInteger(3);
        statsManager.getTasksCompletedPastDays(history,(dayIntTuple)->{
            barDataSet.addEntry(new BarEntry(dayIntTuple.getDate().toEpochDay(),Math.max(dayIntTuple.getNumber(), 0.01f)));
            max.set(Math.max(max.get(), dayIntTuple.getNumber()));
            leftAxis.setAxisMaximum(max.get());
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
        sortingBySelection = selection;
        this.sortBySelectionInt = position;
        if(sortingBySelection != null && dateSelectionMap.containsKey(selection)) {// When first started, sortBySelection will be null, so get by priority is chosen by default.
            buildGraph(Objects.requireNonNull(dateSelectionMap.get(selection)).getDays());
        }else{
            //default to 7 days
            buildGraph( DEFAULT_HISTORY);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;//Do nothing when nothing is selected
    }
}