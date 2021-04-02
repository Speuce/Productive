package com.productive6.productive.ui.title;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.ui.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Activity for selecting title for user
 */
@AndroidEntryPoint
public class TitleSelection extends AppCompatActivity implements ProductiveListener {


    @Inject
    ITitleManager titleManager;

    /**
     * Creates view for the title options and selected title
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_selection);

        EventDispatch.registerListener(this);


        RecyclerView titleView = findViewById(R.id.titleRecyclerView);

        Button submit = findViewById(R.id.submit);

        final TitleAdapter titleAdapter = new TitleAdapter(titleManager);
        titleView.setAdapter(titleAdapter);

        submit.setOnClickListener(v -> openActivity());
    }

    /**
     * Returns to the Main Activity
     */
    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
