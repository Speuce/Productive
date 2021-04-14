package com.productive6.productive.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.ui.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Activity for buying items
 */
@AndroidEntryPoint
public class ShopActivity extends AppCompatActivity {

    @Inject
    IRewardSpenderManager spenderManager;

    @Inject
    ICosmeticManager cosmeticManager;

    View root;

    /**
     * Creates view for the purchasable items
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Button returnButton = findViewById(R.id.returnButtonShop);
        returnButton.setOnClickListener(v -> onBackPressed());

        RecyclerView shopItem = findViewById(R.id.shopItemRecycler);

        root = shopItem.getRootView();

        //attach adapter to shop display
        //Grid View 2 columns
        shopItem.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        shopItem.setAdapter(new ShopAdapter(root,spenderManager,cosmeticManager));
    }

    /**
     * Return to main activity
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(root.getContext(), MainActivity.class));
    }
}
