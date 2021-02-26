package com.productive6.productive.ui.title;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.user.UserManager;
import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;
import com.productive6.productive.objects.events.user.UserTitleInitialized;
import com.productive6.productive.ui.MainActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TitleSelection extends AppCompatActivity implements ProductiveListener {


    private Button submit, cancel;
    private List<Title> titles;
    User person;
    @Inject
    ITitleManager titleManager;
    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_selection);
        //titles = titleManager.getTitleOptions();
        User person = new User();
        person.setSelectedTitle("title1");
        EventDispatch.registerListener(this);

        String oldTitle = person.getSelectedTitle();
        RecyclerView titleView = findViewById(R.id.titleRecyclerView);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);

        final TitleAdapter titleAdapter = new TitleAdapter(titleManager);
        titleView.setAdapter(titleAdapter);
        cancel.setOnClickListener(v -> {
            person.setSelectedTitle(oldTitle);
            openActivity();
        });
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

    @ProductiveEventHandler
    public void userInit(UserLoadedEvent u){
        person = userManager.getCurrentUser();
    }


}
