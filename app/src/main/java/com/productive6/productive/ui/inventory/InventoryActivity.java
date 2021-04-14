package com.productive6.productive.ui.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.ui.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InventoryActivity extends AppCompatActivity {

    @Inject
    ICosmeticManager cosmeticManager;

    View root;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display);
        root = findViewById(android.R.id.content).getRootView();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> onBackPressed());

        //Attach inventory adapter to the inventory display
        RecyclerView inventoryDisplayView = findViewById(R.id.inventoryDisplayView);//Grab display
        InventoryAdapter inventoryAdapter = new InventoryAdapter(root,cosmeticManager);
        inventoryDisplayView.setAdapter(inventoryAdapter);//attach display to view + inventory
        inventoryDisplayView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
    }

    /**
     * Return to main activity
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(root.getContext(), MainActivity.class));
    }
}
