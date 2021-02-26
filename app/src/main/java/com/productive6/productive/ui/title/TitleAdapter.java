package com.productive6.productive.ui.title;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.Title;

import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.titleViewHolder> {
    private ITitleManager titleManager;
    private List<Title> titles;
    private int checkedPosition;

    public TitleAdapter(ITitleManager titleManager) {
        this.titleManager = titleManager;
        titles = titleManager.getTitleOptions();
    }

    @NonNull
    @Override
    public titleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new titleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.title_details,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull titleViewHolder holder, int position) {
        holder.bindTitle(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class titleViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layoutTitle;
        LinearLayout backgroundTitle;
        TextView levelText, titleText;

        public titleViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutTitle = itemView.findViewById(R.id.layoutTitle);
            levelText = itemView.findViewById(R.id.levelText);
            titleText = itemView.findViewById(R.id.titleText);
            backgroundTitle = itemView.findViewById(R.id.backgroundTitle);
            backgroundTitle.setBackgroundColor(Color.BLACK);
        }

        void bindTitle(final Title title) {
            titleText.setText(title.getString());
            levelText.setText("Lv " + title.getLevelRequirement());
            if (titleManager.getTitleAsString().equals(title.getString())) {
                checkedPosition = getAdapterPosition();
                titleManager.setTitle(title.getString());
                backgroundTitle.setBackgroundColor(Color.BLUE);
            }
            else backgroundTitle.setBackgroundColor(Color.BLACK);

            layoutTitle.setOnClickListener(v -> {
                if (!titleManager.getTitleAsString().equals(title.getString())) {
                    notifyItemChanged(checkedPosition);
                    titleManager.setTitle(title.getString());
                    checkedPosition = getAdapterPosition();
                    backgroundTitle.setBackgroundColor(Color.BLUE);
                }
            });
        }
    }
}
