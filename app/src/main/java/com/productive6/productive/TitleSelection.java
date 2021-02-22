package com.productive6.productive;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.productive6.productive.objects.Title;
import com.productive6.productive.objects.User;

import java.util.LinkedList;
import java.util.List;

public class TitleSelection extends AppCompatActivity {
    private Button submit, cancel;
    private String[] titleStrings;
    private int[] titleLevels;
    private final User person = MainActivity.person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_selection);
        String oldTitle = person.getSelectedTitle();
        RecyclerView titleView = findViewById(R.id.titleRecyclerView);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        Resources res = getResources();
        titleStrings = res.getStringArray(R.array.TitleStringArray);
        titleLevels = res.getIntArray(R.array.TitleLevelArray);

        List<Title> titles = new LinkedList<>();

        for (int i = 0; (i < titleStrings.length) && (i < titleLevels.length); i++) {
            if (person.getLevel()>=titleLevels[i])
                titles.add(new Title(titleStrings[i],titleLevels[i]));
        }

        final TitleAdapter titleAdapter = new TitleAdapter(titles,person);
        titleView.setAdapter(titleAdapter);
        cancel.setOnClickListener(v -> {
            person.setSelectedTitle(oldTitle);
            openActivity();
        });
        submit.setOnClickListener(v -> openActivity());
    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
