package com.flipkart.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flipkart.todo.R;
import com.flipkart.todo.Task;
import com.flipkart.todo.TaskStatus;
import com.flipkart.todo.model.TaskTable;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by monish.kumar on 12/12/15.
 */
public class EditTaskFragment extends Fragment {

    String TAG = "EditTaskFragment";
    EditText title = null;
    EditText notes = null;
    Spinner priority = null;

    boolean isDetailView = false;

    public boolean isDetailView() {
        return isDetailView;
    }

    public void setIsDetailView(boolean isDetailView) {
        this.isDetailView = isDetailView;
    }

    Button addBtn;
    Button cancelBtn;
    Button deleteBtn;
    EditText dueDate;
    EditText dueTime;
    Date dueDateTime;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    private int pYear;
    private int pMonth;
    private int pDay;
    static List<String> priorityList;

    private int pHour;
    private int pMin;

    Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                    displayToast();
                }
            };

    private TimePickerDialog.OnTimeSetListener pTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMin = minute;
                    updateTimeDisplay();
                }
            };


    private void updateDisplay() {
        dueDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append("/")
                        .append(pMonth + 1).append("/")
                        .append(pYear).append(" "));

        dueDateTime.setDate(pDay);
        dueDateTime.setMonth(pMonth);
        dueDateTime.setYear(pYear);
    }

    private void updateTimeDisplay() {
        dueTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pHour).append(":")
                        .append(pMin).append(" "));

        dueDateTime.setHours(pHour);
        dueDateTime.setMinutes(pMin);
    }

    private void displayToast() {

    }

    public EditText getDueDate() {
        return dueDate;
    }

    public void setDueDate(EditText dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview = inflater.inflate(R.layout.fragment_add, container, false);
        addBtn = (Button) fragmentview.findViewById(R.id.add);
        addBtn.setText("Edit Task");
        cancelBtn = (Button) fragmentview.findViewById(R.id.cancel);
        deleteBtn = (Button) fragmentview.findViewById(R.id.delete);

        if(isDetailView) {
            cancelBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
        }

        title = (EditText) fragmentview.findViewById(R.id.title);
        notes = (EditText) fragmentview.findViewById(R.id.notes);
        priority = (Spinner) fragmentview.findViewById(R.id.priority);
        dueDate = (EditText) fragmentview.findViewById(R.id.dueDate);
        dueTime = (EditText) fragmentview.findViewById(R.id.dueTime);

        if (priorityList == null) {
            priorityList = Arrays.asList((getResources().getStringArray(R.array.priority)));
        }
        title.setText(task.getTitle());
        notes.setText(task.getNotes());
        priority.setSelection(priorityList.indexOf(task.getPriority()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getDueDate());
        pYear = calendar.get(Calendar.YEAR);
        pMonth = calendar.get(Calendar.MONTH);
        pDay = calendar.get(Calendar.DAY_OF_MONTH);
        pHour = calendar.get(Calendar.HOUR_OF_DAY);
        pMin = calendar.get(Calendar.MINUTE);
        dueDateTime = new Date() ;
        updateDisplay();
        updateTimeDisplay();



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = title.getText().toString();
                String taskNotes = notes.getText().toString();
                task.title = taskTitle;
                task.notes = taskNotes;
                task.priority = priority.getSelectedItem().toString();
//                task.dueDate = dueDate.getText().toString();
                task.status = TaskStatus.pending;
                task.dueDate = dueDateTime;
                Log.i(TAG, task.toString());
                TaskTable.update(task);
                Toast toast = Toast.makeText(getContext(), "Edited Task", Toast.LENGTH_SHORT);
                toast.show();
                if (!isDetailView) {
                    android.support.v4.app.FragmentManager manager = getFragmentManager();
                    manager.popBackStack();
                }
            }
        });
        dueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                             @Override
                                             public void onFocusChange(View v, boolean hasFocus) {
                                                 if (hasFocus) {
                                                     showDialog(DATE_DIALOG_ID).show();
                                                 }
                                             }
                                         }
        );
        dueTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(TIME_DIALOG_ID).show();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setStatus(TaskStatus.deleted);
                TaskTable.update(task);
                Toast toast = Toast.makeText(getContext(), "Task Deleted", Toast.LENGTH_SHORT);
                toast.show();
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        return fragmentview;
    }

    protected Dialog showDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this.getContext(),
                        pDateSetListener,
                        pYear, pMonth, pDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this.getContext(),
                        pTimeSetListener,
                        pHour,
                        pMin,
                        false);
        }
        return null;
    }

}
