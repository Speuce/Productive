package com.productive6.productive.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.objects.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Enabling the translation from data to view, TaskAdapter allows for the modification of the data
 * inside the task list on the dashboard fragment. Task Adapter uses a ViewHolder class to keep the
 * fields inside the task list activity all in one easy-to-access place.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Holds the recyclerView view and it's components
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskName;
        private TextView taskPriority;
        private TextView taskDifficulty;
        private TextView taskDueDate;
        private ToggleButton taskComplete;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskPriority = itemView.findViewById(R.id.taskPriorityTextView);
            taskDifficulty = itemView.findViewById(R.id.taskDifficultyTextView);
            taskDueDate = itemView.findViewById(R.id.taskDueDateTextView);
            taskComplete = itemView.findViewById(R.id.taskCompleteToggleButton);
        }
    }

    public TaskAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_details, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskPriority.setText(""+tasks.get(position).getPriority());
        holder.taskDifficulty.setText(""+tasks.get(position).getDifficulty());
        holder.taskDueDate.setText(format.format(tasks.get(position).getDueDate()));
        holder.taskComplete.setChecked(tasks.get(position).isCompleted());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}
