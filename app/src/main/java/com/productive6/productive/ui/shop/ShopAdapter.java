package com.productive6.productive.ui.shop;

import android.app.AlertDialog;
import android.content.Context;
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
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.logic.rewards.IRewardSpenderManager;
import com.productive6.productive.objects.Cosmetic;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.ArrayList;

/**
 * Enabling the translation from data to view
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements ProductiveListener {

    private ArrayList<Cosmetic> buyableList;

    private Context context;

    private IRewardSpenderManager spenderManager;

    private ICosmeticManager cosmeticManager;

    TextView emptyShop;

    /**
     * Construct the ShopAdapter
     * @param root rootView
     * @param spenderManager manage spending coins
     * @param cosmeticManager manage cosmetics items
     */
    public ShopAdapter(View root, IRewardSpenderManager spenderManager, ICosmeticManager cosmeticManager){
        this.spenderManager = spenderManager;
        this.cosmeticManager = cosmeticManager;
        buyableList = cosmeticManager.getPurchasable();
        emptyShop = root.findViewById(R.id.emptyShop);
        checkEmptyView();
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
        return new ViewHolder(LayoutInflater.from(context).inflate(
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
        holder.coin.setText(String.valueOf(buyableList.get(position).getCost()));
        holder.itemName.setText(buyableList.get(position).getName());
        holder.propImg.setImageResource(buyableList.get(position).getResource());

        if (spenderManager.canSpend(Integer.parseInt(holder.coin.getText().toString()))) {
            holder.buyButton.setEnabled(true);
            holder.buyButton.setOnClickListener(v -> confirmBox(position));
        }
        else holder.buyButton.setEnabled(false);
    }

    /**
     * Confirm Box for spending money
     * @param position position of item
     */
    public void confirmBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage(context.getString(R.string.confirmMessage,String.valueOf(buyableList.get(position).getCost()),buyableList.get(position).getName()));
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    spenderManager.spendCoins(buyableList.get(position).getCost());
                    cosmeticManager.purchaseCosmetic(buyableList.get(position));
                    Toast.makeText(context, "Go to inventory to see your new item!",
                            Toast.LENGTH_SHORT).show();
                    buyableList.remove(position);
                    notifyDataSetChanged();
                    checkEmptyView();
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
        return buyableList.size();
    }

    /**
     * Show TextView when purchasable list of items is empty
     */
    private void checkEmptyView(){
        if (buyableList.isEmpty())
            emptyShop.setVisibility(View.VISIBLE);
        else emptyShop.setVisibility(View.INVISIBLE);
    }
}
