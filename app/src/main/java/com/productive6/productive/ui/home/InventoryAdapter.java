package com.productive6.productive.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.util.CalenderUtilities;
import com.productive6.productive.logic.util.TaskUtilities;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.ui.dashboard.TaskAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements ProductiveListener {

    private List<String> inventory = new ArrayList<>();

    public InventoryAdapter() {
        for (int i = 0; i < 32; i++) {//Fill in the example display while we wait for the backend implementation
            inventory.add("Test Item "+i);
        }
    }

    /**
     * Holds the recyclerView view and it's components
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemName;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

    /**
     * Builds Recycler view that holds a list of dummy status upon creation of ViewHolder.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_list_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(inventory.get(position));
    }

    /**
     * How many items to add to the view.
     * @return
     */
    @Override
    public int getItemCount() {
        return inventory.size();
    }

}
