package com.productive6.productive;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.User;

import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.Title;
import com.productive6.productive.ui.dashboard.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ITitleManager titleManager;
    @Inject
    UserManager userManager;

    private ProgressBar experienceBar;
    private TextView userTitle;
    private TextView coinCounter;
    private TextView levelNumber;
    public static final User person = new User(500,6, 1000);
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

        userManager.load();

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

        userTitle.setOnClickListener(v -> openTitleActivity());
    }

    /*FAKE PLACEHOLDER VALUES REMOVE BEFORE MERGING WITH MASTER
     * These are only to show people how this function works and to initialize the UI
     * As text boxes and progress bars look odd/broken uninitialized
     */
    private void initHeaderPlaceholders(){
        experienceBar.setProgress(person.getExp());
        userTitle.setText(person.getSelectedTitle());
        coinCounter.setText(String.valueOf(person.getCoins()));
        levelNumber.setText(String.valueOf(person.getLevel()));
    }

    public void openTitleActivity() {
        Intent intent = new Intent(this, TitleSelection.class);
        startActivity(intent);
    }

}