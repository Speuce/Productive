package com.productive6.productive.ui.inventory;


import android.app.AlertDialog;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements ProductiveListener {

    private List<String> inventory = new ArrayList<>();
    private View root;

    private int favPosition;

    public InventoryAdapter(View root, int itemCount) {//ItemCount will eventually be replaced with their inventory or an inventory manager
        this.root = root;
        for (int i = 0; i < itemCount; i++) {//Fill in the example display while we wait for the backend implementation
            inventory.add("Test Item "+i);
        }

        //initialize test fav position
        favPosition = 0;
    }

    /**
     * Holds the recyclerView view and it's components
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemName;
        private final ImageView itemImg;
        private final RadioButton starButton;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemImg = itemView.findViewById(R.id.item_img);
            starButton = itemView.findViewById(R.id.starIcon);
        }
    }

    /**
     * Builds Recycler view that holds a list of dummy items upon creation of ViewHolder.
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

    /**
     * setup for each individual item. Get them to display the data in our array list
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resources res = root.getResources();
        holder.itemName.setText(inventory.get(position));
        holder.itemImg.setImageDrawable(res.getDrawable(R.drawable.prop_armor_1,res.newTheme()));//Dynamically set the image
        //Set fav item
        if (favPosition == position) {
            holder.starButton.setChecked(true);
            holder.itemName.setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.pastel_red));
            holder.itemName.setTextColor(ContextCompat.getColor(root.getContext(),R.color.smoke_white));
        }
        else {
            holder.starButton.setChecked(false);
            holder.itemName.setTextColor(ContextCompat.getColor(root.getContext(),R.color.smoke_black));
            holder.itemName.setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.smoke_white));
            holder.starButton.setOnClickListener(v->confirmBox(position));
        }
    }

    /**
     * How many items to add to the view.
     * @return
     */
    @Override
    public int getItemCount() {
        return inventory.size();
    }

    //confirm selecting fav box
    private void confirmBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        //builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage(root.getContext().getString(R.string.confirmFavMessage,inventory.get(position)));
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    favPosition = position;
                    notifyDataSetChanged();
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> notifyDataSetChanged());
        builder.setOnCancelListener(v->notifyDataSetChanged());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
