package com.flipkart.todo;

import android.app.Application;
import android.util.Log;

import com.flipkart.todo.model.DBAdapter;

/**
 * Created by monish.kumar on 30/11/15.
 */
public class ToDoApplication extends Application {

    DBAdapter dbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ToDoApplication", "application started");
        dbAdapter = new DBAdapter(getApplicationContext());
    }
}