package com.productive6.productive.ui.shop;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.ui.MainActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShopActivity extends AppCompatActivity {

    @Inject
    IRewardSpenderManager spenderManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Button returnButton = findViewById(R.id.returnButtonShop);
        returnButton.setOnClickListener(v -> openActivity());

        RecyclerView shopItem = findViewById(R.id.shopItemRecycler);

        //initialize test values
        List<String> coins = Arrays.asList(this.getResources().getStringArray(R.array.priceItem));
        List<String> itemNames = Arrays.asList(this.getResources().getStringArray(R.array.propNameItem));
        TypedArray images = this.getResources().obtainTypedArray(R.array.propItem);
        //attach adapter to shop display
        //Grid View 2 columns
        shopItem.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        shopItem.setAdapter(new ShopAdapter(spenderManager,coins,itemNames,images));
    }

    /**
     * Returns to the Main Activity
     */
    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
