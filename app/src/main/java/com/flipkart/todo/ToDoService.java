package com.flipkart.todo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.flipkart.todo.Activity.MainActivity;
import com.flipkart.todo.model.DBHelper;

import java.util.ArrayList;

/**
 * Created by monish.kumar on 15/12/15.
 */
public class ToDoService extends IntentService{

    public ToDoService() {
        super("ToDo App");
    }

    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this);
            builder.setContentTitle("To Do Items");
            builder.setContentText("Tasks Notification");
            Intent intent1 = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 102, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(android.R.drawable.ic_media_play);


            Notification notification = builder.build();
            notificationManager.notify(1001, notification);
        }
    }

}
