package com.productive6.productive.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.productive6.productive.R;
import com.productive6.productive.ui.title.TitleSelection;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.SystemLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends Fragment implements ProductiveListener {

    @Inject
    ITitleManager titleManager;

    @Inject
    IRewardManager rewardManager;

    private ProgressBar experienceBar;
    private TextView userTitle;
    private TextView coinCounter;
    private TextView levelNumber;

    private HomeViewModel homeViewModel;

    private boolean SystemInitialized;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //register listener
        EventDispatch.registerListener(this);

        //connecting header elements to objects

        experienceBar = (ProgressBar) root.findViewById(R.id.experience_bar);
        userTitle = (TextView) root.findViewById(R.id.user_title);
        coinCounter = (TextView) root.findViewById(R.id.coin_counter);
        levelNumber = (TextView) root.findViewById(R.id.level_number);

        //setting level and title as bold text
        levelNumber.setTypeface(null, Typeface.BOLD);
        userTitle.setTypeface(null, Typeface.BOLD);

        userTitle.setOnClickListener(v -> openTitleActivity());

        updateHeader();

        return root;
    }


    @ProductiveEventHandler
    public void catchSystemLoaded(SystemLoadedEvent event){
        updateHeader();
    }

    private void updateHeader(){
            userTitle.setText(titleManager.getTitleAsString());
            experienceBar.setProgress(rewardManager.getExperience());
            coinCounter.setText("" + rewardManager.getCoins());
            levelNumber.setText("" + rewardManager.getLevel());
    }

    public void openTitleActivity() {
        Intent intent = new Intent(getContext(), TitleSelection.class);
        startActivity(intent);
    }



}