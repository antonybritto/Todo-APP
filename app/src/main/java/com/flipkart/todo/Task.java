package com.flipkart.todo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by antony.britto on 28/11/15.
 */
public class Task implements Parcelable {
    String id;
    String title;
    String notes;
    String priority;
    Date dueDate;
    Date createdDate;
    Date lastModifiedDate;
    TaskStatus status;
    

    public static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            Task c = new Task();
            c.id = source.readString();
            c.title = source.readString();
            c.notes = source.readString();
            c.priority = source.readString();
            c.dueDate = new Date(Long.valueOf(source.readString()));
            c.createdDate = new Date(Long.valueOf(source.readString()));;
            c.lastModifiedDate = new Date(Long.valueOf(source.readString()));;
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(notes);
        dest.writeString(priority);
        dest.writeString(dueDate.toString());
        dest.writeString(createdDate.toString());
        dest.writeString(lastModifiedDate.toString());
        dest.writeString(status.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", notes='" + notes + '\'' +
                ", priority='" + priority + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                ", status=" + status +
                '}';
    }
}
