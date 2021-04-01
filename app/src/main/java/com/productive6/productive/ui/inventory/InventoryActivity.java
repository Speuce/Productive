package com.productive6.productive.ui.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;


public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display);
        View root = findViewById(android.R.id.content).getRootView();
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->onBackPressed());

        //Attach inventory adapter to the inventory display
        RecyclerView inventoryDisplayView = findViewById(R.id.inventoryDisplayView);//Grab display
        InventoryAdapter inventoryAdapter = new InventoryAdapter(root,32);
        inventoryDisplayView.setAdapter(inventoryAdapter);//attach display to view + tasks
        inventoryDisplayView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
    }


}
