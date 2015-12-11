package com.flipkart.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by monish.kumar on 30/11/15.
 */
public class DBAdapter extends SQLiteOpenHelper {

    public DBAdapter(Context context) {
        super(context, //context
                "todoapp.db", //database name
                null, //cursor structure
                5 //version
        );
        TaskTable.init(getWritableDatabase(), getReadableDatabase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TaskTable.dropTable(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
}