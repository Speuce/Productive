package com.productive6.productive;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserTitleInitialized;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TitleSelection extends AppCompatActivity implements ProductiveListener {


    private Button submit, cancel;
    private String[] titleStrings;
    private int[] titleLevels;
    private List<Title> titles;
    @Inject
    ITitleManager titleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_selection);

        EventDispatch.registerListener(this);

        //String oldTitle = person.getSelectedTitle();
        RecyclerView titleView = findViewById(R.id.titleRecyclerView);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        Resources res = getResources();
        titleStrings = res.getStringArray(R.array.TitleStringArray);
        titleLevels = res.getIntArray(R.array.TitleLevelArray);

        List<Title> titles = new LinkedList<>();
/*
        final TitleAdapter titleAdapter = new TitleAdapter(titles,person);
        titleView.setAdapter(titleAdapter);
        cancel.setOnClickListener(v -> {
            person.setSelectedTitle(oldTitle);
            openActivity();
        });*/
        submit.setOnClickListener(v -> openActivity());
    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @ProductiveEventHandler
    public void titleInit(UserTitleInitialized u){
        titles = titleManager.getTitleOptions();
    }

}
