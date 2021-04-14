package com.productive6.productive.ui.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.productive6.productive.R;
import com.productive6.productive.logic.statstics.ITaskStatsManager;
import com.productive6.productive.logic.task.ITaskManager;
import com.productive6.productive.logic.task.ITaskSorter;
import com.productive6.productive.ui.MainActivity;
import com.productive6.productive.ui.dashboard.DashboardFragment;

import java.time.LocalDate;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyAlarmReceiver extends BroadcastReceiver {

    @Inject
    ITaskSorter taskSorter;

    public static final int REQUEST_CODE = 12345;


    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        taskSorter.getTasksOnDate(LocalDate.now(), tasks -> {
            if(tasks.size() > 0){
                //send notification to user.

                Intent intent2 = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "productive")
                        .setSmallIcon(R.mipmap.ic_stat_access_alarm)
                        .setContentTitle("Productive")
                        .setContentText("You have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") +" due today!")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(0, builder.build());
            }
        });

    }
}
