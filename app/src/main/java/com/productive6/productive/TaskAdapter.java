package com.productive6.productive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] tasks;
    String[] difficulties;
    String[] descriptions;

    public TaskAdapter(Context c, String[] t, String[] di, String[] de){
        tasks = t;
        difficulties = di;
        descriptions = de;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.length;
    }

    @Override
    public Object getItem(int position) {
        return tasks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.task_list_details,null);
        TextView taskNameTextView = (TextView) v.findViewById(R.id.taskNameTextView);
        TextView taskDescriptionTextView = (TextView) v.findViewById(R.id.taskDescriptionTextView);
        TextView taskDifficultyTextView = (TextView) v.findViewById(R.id.taskDifficultyTextView);

        String name = tasks[position];
        String desc = descriptions[position];
        String difficulty = difficulties[position];

        taskNameTextView.setText(name);
        taskDescriptionTextView.setText(desc);
        taskDifficultyTextView.setText(difficulty);

        return v;
    }
}
