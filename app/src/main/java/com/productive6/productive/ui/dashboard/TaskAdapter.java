package com.productive6.productive.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.exceptions.ObjectFormatException;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.objects.Task;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.task.TaskCreateEvent;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Holds the recyclerView view and it's components
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
//        private final TextView id;
        private final TextView taskName;
        private final TextView taskPriority;
        private final TextView taskDifficulty;
        private final TextView taskDueDate;
        private final Button taskComplete;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskPriority = itemView.findViewById(R.id.taskPriorityTextView);
            taskDifficulty = itemView.findViewById(R.id.taskDifficultyTextView);
            taskDueDate = itemView.findViewById(R.id.taskDueDateTextView);
            taskComplete = itemView.findViewById(R.id.taskCompleteToggleButton);
            Button editTask = itemView.findViewById(R.id.editButton);
//            id = itemView.findViewById(R.id.taskId);
//            initPopupWindow();

            //listener on 'complete'
            taskComplete.setOnClickListener(v ->{
                try{
                    taskManager.completeTask(tasks.get(getAdapterPosition()));
                    setAnimation(itemView, getAdapterPosition());
                }catch(ObjectFormatException e){
                    taskComplete.setTextColor(0xFF00000);
                    taskComplete.setText("There was an issue with this task..");
                }
            });

            //show edit popup window by clicking 'edit'
            editTask.setOnClickListener((v) -> {
                new TaskPopup(itemView.getContext(), tasks.get(getAdapterPosition()), (task) ->{
                    taskManager.updateTask(task);
                    updateData();
                }).open(v);
            });
        }
    }

    /**
     * Get Android injection from calling method.
     * @param taskManager
     * @param root
     */
    public TaskAdapter(ITaskManager taskManager, View root){
        this.taskManager = taskManager;
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
//        holder.id.setText(""+tasks.get(position).getId());
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskPriority.setText(""+tasks.get(position).getPriorityInString());
        holder.taskDifficulty.setText(""+tasks.get(position).getDifficultyInString());
        if (tasks.get(position).getDueDate() != null)
            holder.taskDueDate.setText(formatter.format(tasks.get(position).getDueDate()));
        else holder.taskDueDate.setText("");
    }

    /**
     * How many items are in the task list.
     * @return
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Sets the items being displayed in the task list.
     * @param tasks
     */
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

    /**
     * Refreshes the tasks being shown on the task list.
     */
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
     * Adds new tasks to current display.
     */
    @ProductiveEventHandler
    public void onNewTask(TaskCreateEvent e){
        this.tasks.add(e.getTask());
        updateData();
    }

}
