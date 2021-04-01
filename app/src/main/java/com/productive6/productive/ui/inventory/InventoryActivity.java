package com.productive6.productive.ui.inventory;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.productive6.productive.R;


public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_display);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->onBackPressed());

    }


}
