package com.productive6.productive.ui.title;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.objects.Title;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Enabling the translation from data to view, TitleAdapter allows for the modification of the data
 * inside the title list on TitleSelection Activity.
 */
public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleViewHolder> {
    private ITitleManager titleManager;
    private Context context;

    /**
     * List of title options
     */
    private List<Title> titles;

    /**
     * Keep the position of selected titles
     */
    private int checkedPosition;

    /**
     * Construct the TitleAdapter
     * @param titleManager Manages title options and selected title
     */
    public TitleAdapter(@NotNull ITitleManager titleManager) {
        this.titleManager = titleManager;
        titles = titleManager.getTitleOptions();
    }

    /**
     * Builds RecyclerView that holds a list of title options upon creation of TitleViewHolder.
     * @param parent Base view
     * @param viewType View type
     * @return Each title view holder
     */
    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TitleViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.title_details,
                        parent,
                        false
                )
        );
    }

    /**
     * Fills the RecyclerView built in onCreateViewHolder with title views using data
     * from the title options list.
     * @param holder Title field view holder
     * @param position Position of title field
     */
    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        holder.bindTitle(titles.get(position));
    }

    /**
     * Number of title options in the list
     * @return size of title options list
     */
    @Override
    public int getItemCount() {
        return titles.size();
    }

    /**
     * Set up display for one title field to bind into RecyclerView
     */
    public class TitleViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layoutTitle;
        LinearLayout backgroundTitle;
        TextView levelText, titleText;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutTitle = itemView.findViewById(R.id.layoutTitle);
            levelText = itemView.findViewById(R.id.levelText);
            titleText = itemView.findViewById(R.id.titleText);
            backgroundTitle = itemView.findViewById(R.id.backgroundTitle);
            titleOption();
        }

        /**
         * Fills the TitleViewHolder with each variable of a Title field,
         * and listen on selected title
         * @param title Title options list
         */
        void bindTitle(final Title title) {
            titleText.setText(title.getString());
            levelText.setText("Lv " + title.getLevelRequirement());
            if (titleManager.getTitleAsString().equals(title.getString())) {
                checkedPosition = getAdapterPosition();
                titleManager.setTitle(title.getString());
                selectedTitle();
            }
            else titleOption();

            layoutTitle.setOnClickListener(v -> {
                if (!titleManager.getTitleAsString().equals(title.getString())) {
                    notifyItemChanged(checkedPosition);
                    titleManager.setTitle(title.getString());
                    checkedPosition = getAdapterPosition();
                    selectedTitle();
                }
            });
        }

        private void selectedTitle(){
            levelText.setTextColor(ContextCompat.getColor(context,R.color.smoke_white));
            titleText.setTextColor(ContextCompat.getColor(context,R.color.smoke_white));
            backgroundTitle.setBackgroundResource(R.drawable.bg_rounded_corners_red);
        }

        private void titleOption(){
            levelText.setTextColor(ContextCompat.getColor(context,R.color.pastel_blue));
            titleText.setTextColor(ContextCompat.getColor(context,R.color.pastel_blue));
            backgroundTitle.setBackgroundResource(R.drawable.bg_rounded_corners_smoke_white);
        }
    }
}