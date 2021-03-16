package com.productive6.productive.ui.statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.productive6.productive.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }


}