package com.productive6.productive.ui.inventory;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;

import java.util.Arrays;
import java.util.List;


public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display);
        View root = findViewById(android.R.id.content).getRootView();
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->onBackPressed());

        //initialize test values
        List<String> itemNames = Arrays.asList(this.getResources().getStringArray(R.array.propNameItem));
        TypedArray images = this.getResources().obtainTypedArray(R.array.propItem);

        //Attach inventory adapter to the inventory display
        RecyclerView inventoryDisplayView = findViewById(R.id.inventoryDisplayView);//Grab display
        InventoryAdapter inventoryAdapter = new InventoryAdapter(root,itemNames,images);
        inventoryDisplayView.setAdapter(inventoryAdapter);//attach display to view + tasks
        inventoryDisplayView.setLayoutManager(new GridLayoutManager(root.getContext(), 4));
    }


}
