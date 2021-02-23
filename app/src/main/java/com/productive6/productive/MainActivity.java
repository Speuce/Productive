package com.productive6.productive;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.Title;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ProgressBar experienceBar;
    private TextView userTitle;
    private TextView coinCounter;
    private TextView levelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        initializeHeader();

        //remove before push to master
        initHeaderPlaceholders();
    }

    /**
     * Initializes the UI objects used in the header
     * so that the progress bar and text boxes can be set
     */
    protected void initializeHeader(){

        //connecting header elements to objects

        experienceBar = (ProgressBar) findViewById(R.id.experience_bar);
        userTitle = (TextView) findViewById(R.id.user_title);
        coinCounter = (TextView) findViewById(R.id.coin_counter);
        levelNumber = (TextView) findViewById(R.id.level_number);

        //setting level and title as bold text
        levelNumber.setTypeface(null, Typeface.BOLD);
        userTitle.setTypeface(null, Typeface.BOLD);
    }

    /*FAKE PLACEHOLDER VALUES REMOVE BEFORE MERGING WITH MASTER
     * These are only to show people how this function works and to initialize the UI
     * As text boxes and progress bars look odd/broken uninitialized
     */
    private void initHeaderPlaceholders(){
        experienceBar.setProgress(60);
        userTitle.setText("Work Horse");
        coinCounter.setText("100");
        levelNumber.setText("23");

    }


    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.new_task_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}