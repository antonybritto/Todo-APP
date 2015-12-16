package com.flipkart.todo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.flipkart.todo.OrderBy;
import com.flipkart.todo.Task;
import com.flipkart.todo.TaskStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by monish.kumar on 30/11/15.
 */
public class TaskTable {

    static SQLiteDatabase writeabledB;
    static SQLiteDatabase readabledB;
    private static final String TAG = "TaskTable";
    private static final String TABLE_NAME = "Task";
    public static final String TITLE = "title";
    public static final String NOTES = "notes";
    public static final String PRIORITY = "priority";
    public static final String STATUS = "status";
    public static final String DUE_DATE = "due_date";
    public static final String CREATED_DATE = "created_date";
    public static final String LAST_MODIFIED = "last_modified";
    public static final String STATE = "state";
    public static final String ID = "_id";
    private static Date date = new Date();


    public static void init(SQLiteDatabase writeEndpoint, SQLiteDatabase readEndpoint) {
        writeabledB = writeEndpoint;
        readabledB =readEndpoint;
        createTable();
    }

    public static void createTable() {
        String query =
                "create table if not exists " + TABLE_NAME + "("+ ID +" integer primary key, " + TITLE + " text  not null, " + NOTES + " text, " + PRIORITY + " INT, " + STATUS + " text, " + DUE_DATE + " DATETIME, " + CREATED_DATE + " DATETIME, " + LAST_MODIFIED + " DATETIME)";
        writeabledB.execSQL(query);
        Log.i(TAG, "Table created");
    }

    public static void dropTable(SQLiteDatabase db) {
        String query = "drop table if exists " +  TABLE_NAME;
        db.execSQL(query);
        Log.i(TAG, "Table deleted");
    }

    public static void insert(Task task){
        try {
            ContentValues row = new ContentValues();
            row.put(TITLE, task.getTitle());
            row.put(NOTES, task.getNotes());
            row.put(PRIORITY, task.getPriority());
            row.put(STATUS, task.getStatus().name());
            row.put(DUE_DATE, task.getDueDate().getTime());
            row.put(CREATED_DATE, new Date().getTime());
            row.put(LAST_MODIFIED, new Date().getTime());
            writeabledB.insertOrThrow(TABLE_NAME, "", row);
//            Log.i(TAG, "Successfully inserted " + task.toString());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private static Task populateTasks(Cursor cursor){
        Task task = null;
        if (cursor != null){
                task = new Task();
                task.setId(cursor.getString(cursor.getColumnIndex(ID)));
                task.setCreatedDate(new java.sql.Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(CREATED_DATE)))));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                task.setDueDate(new java.sql.Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(DUE_DATE)))));
                task.setLastModifiedDate(new java.sql.Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(LAST_MODIFIED)))));
                task.setNotes(cursor.getString(cursor.getColumnIndex(NOTES)));
                task.setPriority(cursor.getString(cursor.getColumnIndex(PRIORITY)));
                task.setStatus(TaskStatus.valueOf(cursor.getString(cursor.getColumnIndex(STATUS))));
        }
        return task;
    }

    private static String appendWhere(Map<String, String> attributeValuePair){
        StringBuilder stringBuilder = null;
        if (attributeValuePair != null &&  attributeValuePair.size() > 0) {
            stringBuilder = new StringBuilder();
            for(Map.Entry<String, String> entry : attributeValuePair.entrySet()) {
                stringBuilder.append(entry.getKey().toString());
                stringBuilder.append(" = ");
                stringBuilder.append("'" + entry.getValue().toString() + "'");

            }
        }
        return stringBuilder!=null? stringBuilder.toString() : null;
    }

    private static String appendOrderBy(Map<String, OrderBy> sortPriority){
        StringBuilder stringBuilder = null;
        if (sortPriority != null &&  sortPriority.size() > 0) {
            stringBuilder = new StringBuilder();
            for(Map.Entry<String, OrderBy> entry : sortPriority.entrySet()) {
                stringBuilder.append(entry.getKey().toString());
                stringBuilder.append(" ");
                stringBuilder.append(entry.getValue().toString());
            }
        }
        return stringBuilder!=null? stringBuilder.toString() : null;
    }

    public static int getCount(Map<String, String> attributeValuePair) {

        Cursor cursor = readabledB.query(TABLE_NAME, null, //columns needed
                appendWhere(attributeValuePair), // coulmns(where)
                null,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public static Task getTask(int position, Map<String, OrderBy> sortPriority, Map<String, String> attributeValuePair) {
        Cursor cursor = readabledB.query(TABLE_NAME, null, //columns needed
                appendWhere(attributeValuePair), // coulmns(where)
                null,
                null,
                null,
                appendOrderBy(sortPriority)
        );
        Task task = null;
        if (cursor.moveToPosition(position)) {
            task  = populateTasks(cursor);
        }
        cursor.close();
        return task;

    }


    public static Task getTask(final long itemId) {
        Cursor cursor = readabledB.query(TABLE_NAME, null, //columns needed
                appendWhere(new HashMap<String, String>() {{
                    put(ID, String.valueOf(itemId));
                }}), // coulmns(where)
                null,
                null,
                null,
                null
        );
        Task task = null;
        if (cursor.moveToFirst()) {
            task  = populateTasks(cursor);
        }
        cursor.close();
        return task;

    }



    public static boolean deleteById(long id){
        return delete(ID, String.valueOf(id));
    }

    public static boolean delete(String attrName, String attrValue){
        return writeabledB.delete(TABLE_NAME, attrName + "= '" + attrValue + "'", null) > 0;
    }



    public static int update(final Task task) {
        ContentValues row = new ContentValues();
        row.put(STATUS, task.getStatus().name());
        row.put(TITLE, task.getTitle());
        row.put(NOTES, task.getNotes());
        row.put(PRIORITY, task.getPriority());
        row.put(STATUS, task.getStatus().name());
        row.put(DUE_DATE, task.getDueDate().getTime());
        row.put(LAST_MODIFIED, new Date().getTime());
        return writeabledB.update(
                TABLE_NAME,
                row,
                appendWhere(new HashMap<String, String>() {{
                    put(ID, String.valueOf(task.getId()));
                }}),
                null
        );


    }

    public static int update(HashMap<String, String> whereClause,HashMap<String, String> columnValurPair) {
        ContentValues row = new ContentValues();
        for (Map.Entry<String, String> entry : columnValurPair.entrySet()) {
            row.put(entry.getKey(), entry.getValue());
        }
        return writeabledB.update(
                TABLE_NAME,
                row,
                appendWhere(whereClause),
                null
        );
    }



}