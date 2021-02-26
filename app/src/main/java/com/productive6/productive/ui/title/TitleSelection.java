package com.productive6.productive.ui.title;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.user.IUserManager;
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


    private Button submit;

    @Inject
    ITitleManager titleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_selection);
        
        EventDispatch.registerListener(this);


        RecyclerView titleView = findViewById(R.id.titleRecyclerView);

        submit = findViewById(R.id.submit);

        final TitleAdapter titleAdapter = new TitleAdapter(titleManager);
        titleView.setAdapter(titleAdapter);

        submit.setOnClickListener(v -> openActivity());
    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
