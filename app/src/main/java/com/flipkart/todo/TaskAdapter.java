package com.flipkart.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.flipkart.todo.model.TaskTable;
import com.flipkart.todo.util.ToDoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antony.britto on 11/12/15.
 */
public class TaskAdapter extends BaseAdapter {
    Context context;
    Map<String, OrderBy> sortPriority = null;
    Map<String, String> attributeValuePair = null;

    public TaskStatus getCheckBoxActionStatus() {
        return checkBoxActionStatus;
    }

    public void setCheckBoxActionStatus(TaskStatus checkBoxActionStatus) {
        this.checkBoxActionStatus = checkBoxActionStatus;
    }

    TaskStatus checkBoxActionStatus;



    private static final String  TAG = "TaskAdapter";

    public TaskAdapter(Context context,  Map<String, OrderBy> sortPriority, Map<String, String> attributeValuePair) {
        this.context = context;
        this.sortPriority = new HashMap<String, OrderBy>();
        this.attributeValuePair = new HashMap<String, String>();
        if (sortPriority != null) {
            this.sortPriority.putAll(sortPriority);
        }
        if (attributeValuePair != null) {
            this.attributeValuePair.putAll(attributeValuePair);
        }
    }

    static class ViewHolder {
        TextView taskPriority;
        TextView title;
        TextView dueDate;
        CheckedTextView checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView = null;
        if(convertView == null) {
            //Log.i("CounterAdapter", "getView(" + position + ")");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mainView = inflater.inflate(R.layout.task, null);
            ViewHolder vh = new ViewHolder();
            vh.taskPriority = (TextView)mainView.findViewById(R.id.priority);
            vh.title = (TextView)mainView.findViewById(R.id.title);
            vh.dueDate = (TextView)mainView.findViewById(R.id.duedate);
            vh.checkBox = (CheckedTextView) mainView.findViewById(R.id.checkBox);
            vh.checkBox.setChecked(false);
            mainView.setTag(vh);
        } else {
            mainView = convertView;
        }
         final Task task = TaskTable.getTask(position, sortPriority, attributeValuePair);

//        Log.i(TAG, sortPriority.toString() + " : " + position + " :" + task.toString());


        ViewHolder vh = (ViewHolder)mainView.getTag();

        vh.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.checkBox);

                if (checkedTextView.isChecked() == true) {
                    checkedTextView.setChecked(false);

                }
                if (checkedTextView.isChecked() == false) {
                    checkedTextView.setChecked(true);

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    task.setStatus(checkBoxActionStatus);
                                    TaskTable.update(task);
                                    checkedTextView.setChecked(false);
                                    notifyDataSetChanged();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    checkedTextView.setChecked(false);
                                    notifyDataSetChanged();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Moving " + task.getTitle() +" task to " + checkBoxActionStatus.name() + " state" ).setPositiveButton("OK", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                }
            }
        });
        vh.checkBox.setChecked(false);

        vh.taskPriority.setText(task.priority);
        vh.title.setText(task.title);
        vh.dueDate.setText(ToDoUtils.getDateString(task.dueDate));
        return mainView;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(TaskTable.getTask(position, sortPriority, attributeValuePair).getId());
    }

    @Override
    public Object getItem(int position) {
        return TaskTable.getTask(position, sortPriority, attributeValuePair);
    }

    @Override
    public int getCount() {
        return TaskTable.getCount(attributeValuePair);
    }

    public Map<String, OrderBy> getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(Map<String, OrderBy> sortPriority) {
        this.sortPriority = sortPriority;
    }

    public Map<String, String> getAttributeValuePair() {
        return attributeValuePair;
    }

    public void setAttributeValuePair(Map<String, String> attributeValuePair) {
        this.attributeValuePair = attributeValuePair;
    }
}
