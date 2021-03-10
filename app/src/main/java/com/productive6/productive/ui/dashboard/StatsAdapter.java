package com.productive6.productive.ui.dashboard;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCreateEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


/**
 * Enabling the translation from data to view, TaskAdapter allows for the modification of the data
 * inside the task list on the dashboard fragment. Task Adapter uses a ViewHolder class to keep the
 * fields inside the task list activity all in one easy-to-access place.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> implements ProductiveListener {

    @Inject
    ITaskManager taskManager;

    /**
     * List of tasks to be displayed.
     */
    private List<> stats = new ArrayList<>();

    private final View rootView;

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);

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
     * Get Android injection from calling method.
     * @param root
     */
    public StatsAdapter(View root){
        this.rootView = root;
    }

    /**
     * Builds Recycler view that holds a list of tasks upon creation of ViewHolder.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_details, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Fills the Recycler view built in onCreateViewHolder with task views using data from the task list.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.statName.setText(stats.get(position).getName());
        holder.statValue.setText(stats.get(position).getValue());
    }

    /**
     * How many items are in the task list.
     * @return
     */
    @Override
    public int getItemCount() {
        return stats.size();
    }

    /**
     * Sets the items being displayed in the task list.
     * @param stats
     */
    public void setStats(List<Stat> stats){
        this.stats = stats;
    }
}
