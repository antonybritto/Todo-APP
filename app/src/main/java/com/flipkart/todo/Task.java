package com.flipkart.todo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

/**
 * Created by antony.britto on 28/11/15.
 */
public class Task implements Parcelable {
    String title;
    String notes;
    String priority;
    String dueDate;
    String createdDate;
    String lastModifiedDate;
    TaskStatus status;

    public static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            Task c = new Task();
            c.title = source.readString();
            c.notes = source.readString();
            c.priority = source.readString();
            c.dueDate = source.readString();
            c.createdDate = source.readString();
            c.lastModifiedDate = source.readString();
            c.status = TaskStatus.valueOf(source.readString());
            return c;
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(notes);
        dest.writeString(priority);
        dest.writeString(dueDate);
        dest.writeString(createdDate);
        dest.writeString(lastModifiedDate);
        dest.writeString(status.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
