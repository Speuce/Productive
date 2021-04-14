package com.productive6.productive.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.productive6.productive.R;
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.rewards.IRewardManager;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.system.SystemLoadedEvent;
import com.productive6.productive.ui.inventory.InventoryActivity;
import com.productive6.productive.ui.shop.ShopActivity;
import com.productive6.productive.ui.title.TitleSelection;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends Fragment implements ProductiveListener {

    @Inject
    ITitleManager titleManager;

    @Inject
    IRewardManager rewardManager;

    @Inject
    ICosmeticManager cosmeticManager;

    private ProgressBar experienceBar;
    private TextView userTitle;
    private TextView coinCounter;
    private TextView levelNumber;
    private ImageView favItem;
    private TextView emptyFavItem;


    /**
     * Creates the view by inflating the layout
     * Initializes all the header elements with objects to allow for updating
     * updates the values of the header elements
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //register listener
        EventDispatch.registerListener(this);

        //connecting header elements to objects
        experienceBar = (ProgressBar) root.findViewById(R.id.experience_bar);
        userTitle = (TextView) root.findViewById(R.id.user_title);
        coinCounter = (TextView) root.findViewById(R.id.coin_counter);
        levelNumber = (TextView) root.findViewById(R.id.level_number);
        Button buttonTitle = (Button) root.findViewById(R.id.selectTitle);
        Button buttonShop = (Button) root.findViewById(R.id.shopButton);

        //setting level and title as bold text
        levelNumber.setTypeface(null, Typeface.BOLD);
        userTitle.setTypeface(null, Typeface.BOLD);
        buttonTitle.setOnClickListener(v -> openTitleActivity());
        userTitle.setOnClickListener(v -> openTitleActivity());
        buttonShop.setOnClickListener(v -> openShopActivity());
        updateHeader();

        //Attach button to inventory activity
        root.findViewById(R.id.inventoryButton).setOnClickListener(view -> startActivity(new Intent(getContext(), InventoryActivity.class)));

        //Display the user's favorite item
        favItem = root.findViewById(R.id.propFavImg);
        emptyFavItem = root.findViewById(R.id.emptyFavItem);
        displayFavItem();
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
        displayFavItem();
    }

    /**
     * Updates the header to all the current values
     */
    private void updateHeader() {
       if(rewardManager.isInitialized()) {
           userTitle.setText(titleManager.getTitleAsString());
           experienceBar.setProgress(rewardManager.getExperience());
           coinCounter.setText(String.valueOf(rewardManager.getCoins()));
           levelNumber.setText(String.valueOf(rewardManager.getLevel()));
       }
    }

    /**
     * When no fav item, show textview
     */
    private void displayFavItem(){
        if (cosmeticManager.isInitialized()) {
            if (cosmeticManager.getFavorite() == null)
                emptyFavItem.setVisibility(View.VISIBLE);
            else {
                emptyFavItem.setVisibility(View.INVISIBLE);
                favItem.setImageResource(cosmeticManager.getFavorite().getResource());
            }
        }
    }

    /**
     * Opens the title selection activity
     * when the user's title or the button is clicked
     */
    public void openTitleActivity() {
        Intent intent = new Intent(getContext(), TitleSelection.class);
        startActivity(intent);
    }

    /**
     * Opens the shop activity
     * when the shop button is clicked
     */
    public void openShopActivity() {
        Intent intent = new Intent(getContext(), ShopActivity.class);
        startActivity(intent);
    }
}