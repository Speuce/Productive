package com.productive6.productive.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.productive6.productive.R;
import com.productive6.productive.logic.rewards.ITitleManager;
import com.productive6.productive.logic.user.IUserManager;

import com.productive6.productive.objects.Task;


import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.ui.title.TitleSelection;


import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ITaskManager taskManager;
    @Inject
    ITitleManager titleManager;
    @Inject
    IUserManager userManager;

    private View popupView;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_rewards, R.id.navigation_todo, R.id.navigation_schedule)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        userManager.load();

        initPopupWindow();

    }

    /**
     * Initializes the 'create task' popup window.
     * code from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
     *
     */
    protected void initPopupWindow(){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_task_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setFocusable(true);

        popupView.setOnTouchListener((v, event) -> {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                popupView.requestFocus();
                v.performClick();
                return false;
        });
    }


    public void openTitleActivity() {
        Intent intent = new Intent(this, TitleSelection.class);
        startActivity(intent);
    }

    /**
     * Shows 'add task' popup on 'add' button press.
     * @param view
     *
     */
    public void onButtonShowPopupWindowClick(View view) {

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

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
     * On pressing the 'submit' button on the 'add task' popup, create new task object and submit it to the database.
     * @param view
     */
    public void addTask(View view){
        EditText name = popupView.findViewById(R.id.taskNameForm);
        taskManager.addTask(new Task(name.getText().toString(),1,1,0, new Date(),false));
        popupWindow.dismiss();
        name.setText("");
    }
}