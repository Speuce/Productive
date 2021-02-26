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

import javax.inject.Inject;


/**
 * Enabling the translation from data to view, TaskAdapter allows for the modification of the data
 * inside the task list on the dashboard fragment. Task Adapter uses a ViewHolder class to keep the
 * fields inside the task list activity all in one easy-to-access place.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements ProductiveListener {

    @Inject
    ITaskManager taskManager;

    /**
     * List of tasks to be displayed.
     */
    private List<Task> tasks = new ArrayList<>();

    private final View rootView;

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Holds the recyclerView view and it's components
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView taskName;
        private TextView taskPriority;
        private TextView taskDifficulty;
        private TextView taskDueDate;
        private Button taskComplete;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskPriority = itemView.findViewById(R.id.taskPriorityTextView);
            taskDifficulty = itemView.findViewById(R.id.taskDifficultyTextView);
            taskDueDate = itemView.findViewById(R.id.taskDueDateTextView);
            taskComplete = itemView.findViewById(R.id.taskCompleteToggleButton);
            id = itemView.findViewById(R.id.taskId);

            //listener on 'complete'
            taskComplete.setOnClickListener(v ->{
                try{
                    taskManager.completeTask(tasks.get(getAdapterPosition()));
                    setAnimation(itemView, getAdapterPosition());
                }catch(ObjectFormatException e){
                    taskComplete.setTextColor(0xFF00000);
                    taskComplete.setText("BLAHH");
                }

            });
        }
    }

    public TaskAdapter(ITaskManager taskManager, View root){
        this.taskManager = taskManager;
        this.rootView = root;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(""+tasks.get(position).getId());
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskPriority.setText(""+tasks.get(position).getPriority());
        holder.taskDifficulty.setText(""+tasks.get(position).getDifficulty());
        holder.taskDueDate.setText(format.format(tasks.get(position).getDueDate()));
//        holder.taskComplete.setChecked(tasks.get(position).isCompleted());
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        updateData();

    }
    /**
     * Applies an animation to the given view.
     * Code from: https://stackoverflow.com/a/26748274/6047183
     */
    private void setAnimation(View viewToAnimate, int position) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_out);
            animation.setDuration(300);
            viewToAnimate.startAnimation(animation);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                tasks.remove(position);
                updateData();
            }, animation.getDuration()+20);
    }


    private void updateData(){
        RecyclerView taskDisplayView = rootView.findViewById(R.id.taskDisplayView);
        TextView emptyView = rootView.findViewById(R.id.empty_view);
        if (tasks.isEmpty()) {
            taskDisplayView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            taskDisplayView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
            notifyDataSetChanged();
        }
    }

    /**
     * Method called when a new task is created.
     */
    @ProductiveEventHandler
    public void onNewTask(TaskCreateEvent e){
        this.tasks.add(e.getTask());
        updateData();
    }

}
