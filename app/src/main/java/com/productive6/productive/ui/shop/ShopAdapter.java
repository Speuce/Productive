package com.productive6.productive.ui.shop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.List;

/**
 * Enabling the translation from data to view
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements ProductiveListener {

    /**
     * List of cosmetics' prices
     */
    private int[] coins;
    /**
     * List of cosmetics' names
     */
    private List<String> itemNames;
    /**
     * List of cosmetics' images
     */
    private TypedArray images;

    private Context context;

    private IRewardSpenderManager spenderManager;
    /**
     * Construct the ShopAdapter
     * @param coins list of prices
     * @param images list of images for prop
     */
    public ShopAdapter(IRewardSpenderManager spenderManager, int[] coins,List<String> itemNames, TypedArray images){
        this.spenderManager = spenderManager;
        this.itemNames = itemNames;
        this.coins = coins;
        this.images = images;
    }

    /**
     * Set up display recyclerview for cosmetics shop item
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView coin;
        ImageView propImg;
        TextView itemName;
        Button buyButton;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            coin = itemView.findViewById(R.id.price);
            itemName = itemView.findViewById(R.id.propName);
            propImg = itemView.findViewById(R.id.propImg);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }

    /**
     * Builds RecyclerView that holds a list of cosmetics buying options.
     * @param parent Base view
     * @param viewType View type
     * @return Each prop view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
            R.layout.shop_item,
            parent,
            false
            )
        );
    }

    /**
     * Fills the RecyclerView built in onCreateViewHolder with prop views using data
     * @param holder Prop field view holder
     * @param position Position of prop field
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.coin.setText(String.valueOf(coins[position]));
        holder.itemName.setText(itemNames.get(position));
        holder.propImg.setImageResource(images.getResourceId(position,0));

        if (spenderManager.canSpend(Integer.parseInt(holder.coin.getText().toString()))) {
            holder.buyButton.setEnabled(true);
            holder.buyButton.setOnClickListener(v -> confirmBox(position));
        }
        else holder.buyButton.setEnabled(false);
    }

    //confirm buying box
    public void confirmBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage(context.getString(R.string.confirmMessage,String.valueOf(coins[position]),itemNames.get(position)));
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    spenderManager.spendCoins(coins[position]);
                    Toast toast = Toast.makeText(context, "Go to inventory to see your new item!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    notifyDataSetChanged();
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Number of cosmetics items in the list
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return itemNames.size();
    }
}
