package com.productive6.productive.ui.stats;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.productive6.productive.R;
import com.productive6.productive.objects.events.ProductiveListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 *
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> implements ProductiveListener {

    /**
     * Internal list of String-String tuples representing the statistics
     */
    private final LinkedHashMap<String, String> stats;


    public StatsAdapter() {
        this.stats = new LinkedHashMap<>();

    }



    /**
     * Holds the recyclerView view and it's components
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView statName;
        private final TextView statValue;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            statName = itemView.findViewById(R.id.stat_name);
            statValue = itemView.findViewById(R.id.stat_value);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_details, parent, false);
        return new ViewHolder(view);
    }

    public void setStat(String statistic, String value){
        this.stats.put(statistic, value);
        notifyDataSetChanged();
    }


    /**
     * Fills the Recycler view built in onCreateViewHolder with task views
     * @param holder
     * @param position
     */
    @Override
    @SuppressWarnings("unchecked")//this suppression is here because java sucks.
    //see https://stackoverflow.com/questions/509076/how-do-i-address-unchecked-cast-warnings
    //the compiler cannot know that the cast is safe, but i do.
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, String> stat = (Map.Entry<String, String>) stats.entrySet().toArray()[position];
        holder.statName.setText(stat.getKey());
        holder.statValue.setText(stat.getValue());
    }

    /**
     * How many items to add to the view.
     * @return
     */
    @Override
    public int getItemCount() {
        return stats.size();
    }

}
