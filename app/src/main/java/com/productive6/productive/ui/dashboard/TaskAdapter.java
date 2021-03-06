package com.productive6.productive.ui.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
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

    private View popupView;

    private PopupWindow popupWindow;

    private DatePickerDialog datePickerDialog;

    private Calendar calendar;

    /**
     * For formatting dates in the view
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);

    /**
     * Holds the recyclerView view and it's components
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView id;
        private final TextView taskName;
        private final TextView taskPriority;
        private final TextView taskDifficulty;
        private final TextView taskDueDate;
        private final Button taskComplete;
        private final Button editTask;
        Task task;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskPriority = itemView.findViewById(R.id.taskPriorityTextView);
            taskDifficulty = itemView.findViewById(R.id.taskDifficultyTextView);
            taskDueDate = itemView.findViewById(R.id.taskDueDateTextView);
            taskComplete = itemView.findViewById(R.id.taskCompleteToggleButton);
            editTask = itemView.findViewById(R.id.editButton);
            id = itemView.findViewById(R.id.taskId);
            initPopupWindow();

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

            editTask.setOnClickListener(this::editPopupWindow);
        }

        protected void initPopupWindow(){

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.edit_task_popup, null);
            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setFocusable(true);

            popupView.setOnTouchListener((v, event) -> {
                InputMethodManager imm =  (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                popupView.requestFocus();
                v.performClick();
                return false;
            });
        }

        public void editPopupWindow(View view) {
            task = tasks.get(getAdapterPosition());
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            initDatePicker();
            initPriority();
            initDifficulty();
            dimBehind(popupWindow);

            Button submit = popupView.findViewById(R.id.submit);
            submit.setOnClickListener(v -> {
                EditText name = popupView.findViewById(R.id.taskNameForm);

                SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);

                RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
                RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                int priority = radioGroup.indexOfChild(radioButton);

                RadioGroup radioDiffGroup = popupView.findViewById(R.id.difficultyGroup);
                RadioButton radioDiffButton = radioDiffGroup.findViewById(radioDiffGroup.getCheckedRadioButtonId());
                int difficulty = radioDiffGroup.indexOfChild(radioDiffButton);

                task.setDifficulty(difficulty+1);
                task.setPriority(priority+1);
                task.setTaskName(name.getText().toString());
                if (hasDeadline.isChecked()) {
                    task.setDueDate(calendar.getTime());
                } else {
                    task.setDueDate(null);
                }
                taskManager.updateTask(task);
                popupWindow.dismiss();
                updateData();
            });
        }

        /**
         * Dims the background behind a given popup window
         * @param popupWindow the popupwindow to dim around
         * Code from: https://stackoverflow.com/a/46711174/6047183
         */
        public void dimBehind(PopupWindow popupWindow) {
            View container = popupWindow.getContentView().getRootView();
            Context context = popupWindow.getContentView().getContext();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            wm.updateViewLayout(container, p);
        }

        /**
         * Initializes the date picker window
         */
        private void initDatePicker() {
            Button dateButton = popupView.findViewById(R.id.datePickerButton);
            SwitchCompat hasDeadline = popupView.findViewById(R.id.switchDeadline);
            EditText name = popupView.findViewById(R.id.taskNameForm);

            calendar = Calendar.getInstance();

            name.setText(taskName.getText());
            if (taskDueDate.getText() == "") {
                hasDeadline.setChecked(false);
                dateButton.setTextColor(Color.GRAY);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            }
            else {
                hasDeadline.setChecked(true);
                calendar.setTime(task.getDueDate());
                dateButton.setTextColor(Color.BLACK);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }
            dateButton.setText(format.format(calendar.getTime()));

            //Toggle the button if this task has due date
            hasDeadline.setOnClickListener(v -> {
                if (hasDeadline.isChecked()) {
                    dateButton.setTextColor(Color.BLACK);
                    dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                } else {
                    dateButton.setTextColor(Color.GRAY);
                    dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                }
            });

            //Set the calendar date to the date user selected
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
                hasDeadline.setChecked(true);
                dateButton.setTextColor(Color.BLACK);
                dateButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();
                dateButton.setText(format.format(date));
            };

            //Initialize date picker dialog
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(itemView.getContext(), dateSetListener, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            //On clicking on date picker button on popup window, open dialog to choose date.
            dateButton.setOnClickListener(v -> datePickerDialog.show());
        }

        /**
         * Initializes the priority default choice
         */
        private void initPriority() {
            RadioGroup radioGroup = popupView.findViewById(R.id.priorityGroup);
            RadioButton oldChoice = (RadioButton) radioGroup.getChildAt(task.getPriority()-1);
            oldChoice.setChecked(true);

        }

        /**
         * Initializes the difficulty default choice
         */
        private void initDifficulty() {
            RadioGroup radioGroup = popupView.findViewById(R.id.difficultyGroup);
            RadioButton oldChoice = (RadioButton) radioGroup.getChildAt(task.getDifficulty()-1);
            oldChoice.setChecked(true);
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
        holder.id.setText(""+tasks.get(position).getId());
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskPriority.setText(""+tasks.get(position).getPriority());
        holder.taskDifficulty.setText(""+tasks.get(position).getDifficulty());
        if (tasks.get(position).getDueDate() != null)
            holder.taskDueDate.setText(format.format(tasks.get(position).getDueDate()));
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
