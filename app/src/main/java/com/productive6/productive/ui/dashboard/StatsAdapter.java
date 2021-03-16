package com.productive6.productive.ui.dashboard;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.productive6.productive.R;
import com.productive6.productive.objects.events.ProductiveListener;

/**
 *
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> implements ProductiveListener {


    /**
     * Holds the recyclerView view and it's components
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
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

    /**
     * Fills the Recycler view built in onCreateViewHolder with task views using fake data.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.statName.setText("Stat Name "+position);
        holder.statValue.setText("Stat Value "+position);
    }

    /**
     * How many items to add to the view.
     * @return
     */
    @Override
    public int getItemCount() {
        return 3;
    }

}
