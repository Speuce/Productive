package com.productive6.productive.ui.inventory;


import android.app.AlertDialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

import java.util.List;

/**
 *
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements ProductiveListener {

    /**
     * List of cosmetics' names
     */
    private List<String> itemNames;
    /**
     * List of cosmetics' images
     */
    private TypedArray images;

    private View root;

    private int favPosition;

    public InventoryAdapter(View root, List<String> itemNames, TypedArray images) {
        this.root = root;
        this.itemNames = itemNames;
        this.images = images;

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
        private final ImageView clickField;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemImg = itemView.findViewById(R.id.item_img);
            starButton = itemView.findViewById(R.id.starIcon);
            clickField = itemView.findViewById(R.id.clickField);
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
        holder.itemName.setText(itemNames.get(position));
        holder.itemImg.setImageResource(images.getResourceId(position,0));//Dynamically set the image
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
            holder.clickField.setOnClickListener(v->confirmBox(position));
        }
    }

    /**
     * How many items to add to the view.
     * @return
     */
    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    //confirm selecting fav box
    private void confirmBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        //builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage(root.getContext().getString(R.string.confirmFavMessage,itemNames.get(position)));
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    favPosition = position;
                    notifyDataSetChanged();
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
