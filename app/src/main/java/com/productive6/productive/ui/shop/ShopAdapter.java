package com.productive6.productive.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    List<String> coins;
    List<Integer> images;
    LayoutInflater inflater;

    public ShopAdapter(Context context, List<String> coins, List<Integer> images){
        this.coins = coins;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shop_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.coin.setText(coins.get(position));
        holder.propImg.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }
}
