package com.productive6.productive.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.productive6.productive.R;
import com.productive6.productive.ui.title.TitleSelection;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.SystemLoadedEvent;
import com.productive6.productive.objects.events.user.UserUpdateEvent;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private ImageSlider imageSlider;
    TextView textView;

    /**
     * Creates the view by inflating the layout
     * Initializes all the header elements with objects to allow for updating
     * updates the values of the header elements
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //register listener
        EventDispatch.registerListener(this);

        //connecting header elements to objects
        experienceBar = (ProgressBar) root.findViewById(R.id.experience_bar);
        userTitle = (TextView) root.findViewById(R.id.user_title);
        coinCounter = (TextView) root.findViewById(R.id.coin_counter);
        levelNumber = (TextView) root.findViewById(R.id.level_number);

//        textView = root.findViewById(R.id.text_home);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        //setting level and title as bold text
        levelNumber.setTypeface(null, Typeface.BOLD);
        userTitle.setTypeface(null, Typeface.BOLD);
        userTitle.setOnClickListener(v -> openTitleActivity());
        updateHeader();
        return root;
    }


    /**
     * When the system has been fully loaded, this method updates the header
     *
     * @param event symbolizes the system having loaded
     */
    @ProductiveEventHandler
    public void catchSystemLoaded(SystemLoadedEvent event) {
        updateHeader();
    }

    /**
     * Updates the header to all the current values
     */
    private void updateHeader() {
        userTitle.setText(titleManager.getTitleAsString());
        experienceBar.setProgress(rewardManager.getExperience());
        coinCounter.setText("" + rewardManager.getCoins());
        levelNumber.setText("" + rewardManager.getLevel());
    }

    /**
     * Opens the title selection activity
     * when the user's title is clicked
     */
    public void openTitleActivity() {
        Intent intent = new Intent(getContext(), TitleSelection.class);
        startActivity(intent);
    }
}