package com.productive6.productive;

import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar experienceBar;
    private TextView username;
    private TextView userTitle;
    private TextView coinCounter;

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


        //connecting header elements to objects
        experienceBar = (ProgressBar) findViewById(R.id.experience_bar);
        username = (TextView) findViewById(R.id.user_name);
        userTitle = (TextView) findViewById(R.id.user_title);
        coinCounter = (TextView) findViewById(R.id.coin_counter);

        //setting username as bold text
        username.setTypeface(null, Typeface.BOLD);

        //fake values
        experienceBar.setProgress(60);
        username.setText("Luke Morrow");
        userTitle.setText("Work Horse");
        coinCounter.setText("100");


    }

}