package com.flipkart.todo.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.flipkart.todo.OrderBy;
import com.flipkart.todo.TaskAdapter;
import com.flipkart.todo.model.TaskTable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by monish.kumar on 12/12/15.
 */
public class ToDoUtils {

    public static String getDateString(Date timeStamp){

        StringBuilder stringBuilder = new StringBuilder();
        if(timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            Date date = new java.sql.Date(timeStamp.getTime());
            calendar.setTime(date);
            stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH)).append("/")
                    .append(calendar.get(Calendar.MONTH) + 1).append("/")
                    .append(calendar.get(Calendar.YEAR)).append(" ")
                    .append(calendar.get(Calendar.HOUR_OF_DAY))
                    .append(":")
                    .append(calendar.get(Calendar.MINUTE))
                    .append(" ")
                    .toString();
        }
        return stringBuilder.toString();
    }

    public static void  setSpinnerOnClickListener(Spinner spinner, final TaskAdapter adapter){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adapter != null && adapter.getSortPriority() != null){
                    adapter.getSortPriority().clear();

                    switch (position) {
                        case 0:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.DESC);
                            break;
                        case 1:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.ASC);
                            break;
                        case 2:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.DESC);
                            break;
                        case 3:
                            adapter.getSortPriority().put(TaskTable.PRIORITY, OrderBy.ASC);
                            break;
                        case 4:
                            adapter.getSortPriority().put(TaskTable.PRIORITY, OrderBy.DESC);
                            break;
                        case 5:
                            adapter.getSortPriority().put(TaskTable.TITLE, OrderBy.DESC);
                            break;
                    }
                    adapter.notifyDataSetInvalidated();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
