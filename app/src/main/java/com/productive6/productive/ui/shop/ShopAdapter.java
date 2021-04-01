package com.productive6.productive.ui.shop;

import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.List;

/**
 * Enabling the translation from data to view
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements ProductiveListener {

    /**
     * List of cosmetics' prices
     */
    List<String> coins;
    /**
     * List of cosmetics' images
     */
    TypedArray images;

    /**
     * Construct the ShopAdapter
     * @param coins list of prices
     * @param images list of images for prop
     */
    public ShopAdapter(List<String> coins, TypedArray images){
        this.coins = coins;
        this.images = images;
    }

    /**
     * Set up display recyclerview for cosmetics shop item
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView coin;
        ImageView propImg;
        Button buyButton;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            coin = itemView.findViewById(R.id.price);
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
        holder.coin.setText(coins.get(position));
        holder.propImg.setImageResource(images.getResourceId(position,0));
    }

    /**
     * Number of cosmetics items in the list
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return coins.size();
    }
}
