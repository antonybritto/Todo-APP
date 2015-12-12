package com.flipkart.todo.util;

import java.util.Date;

/**
 * Created by monish.kumar on 12/12/15.
 */
public class ToDoUtils {

    public static String getDateString(Date timeStamp){

        StringBuilder stringBuilder = new StringBuilder();
        if(timeStamp != null) {
            Date date = new java.sql.Date(timeStamp.getTime());
            stringBuilder
                    .append(date.getDate()).append("/")
                    .append(date.getMonth() + 1).append("/")
                    .append(date.getYear()).append(" ").toString();
        }
        return stringBuilder.toString();
    }

}
