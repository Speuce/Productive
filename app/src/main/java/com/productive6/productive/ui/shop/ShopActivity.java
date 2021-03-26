package com.productive6.productive.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    List<String> coins;
    List<Integer> images;
    ShopAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Button returnButton = findViewById(R.id.returnButtonShop);
        returnButton.setOnClickListener(v -> openActivity());

        RecyclerView shopItem = findViewById(R.id.shopItemRecycler);

        coins = new ArrayList<>();
        images = new ArrayList<>();

        coins.add("10");
        coins.add("20");
        coins.add("30");
        coins.add("40");
        coins.add("50");
        coins.add("60");
        coins.add("70");
        coins.add("80");
        coins.add("90");
        coins.add("100");

        images.add(R.drawable.prop_armor_1);
        images.add(R.drawable.prop_armor_2);
        images.add(R.drawable.prop_armor_3);
        images.add(R.drawable.prop_armor_4);
        images.add(R.drawable.prop_arrow_1);
        images.add(R.drawable.prop_arrow_2);
        images.add(R.drawable.prop_arrow_3);
        images.add(R.drawable.prop_arrow_4);
        images.add(R.drawable.prop_bow_1);
        images.add(R.drawable.prop_bow_2);
        images.add(R.drawable.prop_bow_3);

        adapter = new ShopAdapter(this,coins,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        shopItem.setLayoutManager(gridLayoutManager);
        shopItem.setAdapter(adapter);
    }

    /**
     * Returns to the Main Activity
     */
    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
