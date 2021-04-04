package com.productive6.productive.ui.inventory;


import android.app.AlertDialog;
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
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.objects.Cosmetic;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.ArrayList;

/**
 * Enabling the translation from data to view
 */
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements ProductiveListener {

    private View root;

    private ICosmeticManager cosmeticManager;

    private ArrayList<Cosmetic> ownedList;

    /**
     * Construct the InventoryAdapter
     * @param root rootView
     * @param cosmeticManager manage cosmetics items
     */
    public InventoryAdapter(View root, ICosmeticManager cosmeticManager) {
        this.root = root;
        this.cosmeticManager = cosmeticManager;
        ownedList = cosmeticManager.getOwnedItems();

        //show textview when no items in inventory
        TextView emptyInventory = root.findViewById(R.id.emptyInventory);
        if (ownedList.isEmpty())
            emptyInventory.setVisibility(View.VISIBLE);
        else emptyInventory.setVisibility(View.INVISIBLE);
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
     * @param parent parent view
     * @param viewType view type
     * @return return view holder for recycler view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_list_details, parent, false);
        return new ViewHolder(view);
    }

    /**
     * setup for each individual item. Get them to display the data in our array list
     * @param holder viewholder for each item
     * @param position position of each item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(ownedList.get(position).getName());
        holder.itemImg.setImageResource(ownedList.get(position).getResource());//Dynamically set the image
        //Set fav item
        if (cosmeticManager.getFavorite() != null && ownedList.get(position).getId() == cosmeticManager.getFavorite().getId()) {
            holder.starButton.setChecked(true);
            holder.itemName.setTextColor(ContextCompat.getColor(root.getContext(),R.color.smoke_white));
            holder.itemName.setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.pastel_red));
            String messageString = root.getContext().getString(R.string.confirmNoFav,ownedList.get(position).getName());
            holder.clickField.setOnClickListener(v->confirmBox(-1,messageString));
        }
        else {
            holder.starButton.setChecked(false);
            holder.itemName.setTextColor(ContextCompat.getColor(root.getContext(),R.color.smoke_black));
            holder.itemName.setBackgroundColor(ContextCompat.getColor(root.getContext(),R.color.smoke_white));
            String messageString = root.getContext().getString(R.string.confirmFavMessage,ownedList.get(position).getName());
            holder.clickField.setOnClickListener(v->confirmBox(position,messageString));
        }
    }

    /**
     * How many items to add to the view.
     * @return number of items in view
     */
    @Override
    public int getItemCount() {
        return ownedList.size();
    }

    /**
     * Confirm Box for selecting and deselecting fav item
     * @param position position of chosen favorite item
     * @param messageString message on confirm box
     */
    private void confirmBox(int position,String messageString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setTitle("Confirm");
        builder.setMessage(messageString);
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    if (position == -1)
                        cosmeticManager.setFavorite(null);
                    else cosmeticManager.setFavorite(ownedList.get(position));
                    notifyDataSetChanged();
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
