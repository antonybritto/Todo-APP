package com.flipkart.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
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
import android.widget.Toast;

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

    Button addBtn;
    Button cancelBtn;
    EditText dueDate;
    Date dueDateTime;
    static final int DATE_DIALOG_ID = 0;
    private int pYear;
    private int pMonth;
    private int pDay;
    static List<String> priorityList;


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
        title = (EditText) fragmentview.findViewById(R.id.title);
        notes = (EditText) fragmentview.findViewById(R.id.notes);
        priority = (Spinner) fragmentview.findViewById(R.id.priority);
        dueDate = (EditText) fragmentview.findViewById(R.id.dueDate);

        if (priorityList == null) {
            priorityList = Arrays.asList((getResources().getStringArray(R.array.priority)));
        }
        title.setText(task.getTitle());
        notes.setText(task.getNotes());
        priority.setSelection(priorityList.indexOf(task.getPriority()));

        pYear = task.getDueDate().getYear();
        pMonth = task.getDueDate().getMonth();
        pDay = task.getDueDate().getDate();
        dueDateTime = new Date() ;
        updateDisplay();



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
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        dueDate.setOnFocusChangeListener( new View.OnFocusChangeListener() {
                                              @Override
                                              public void onFocusChange(View v, boolean hasFocus) {
                                                  if (hasFocus) {
                                                      showDialog(DATE_DIALOG_ID).show();
                                                  }
                                              }
                                          }
        );
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
        }
        return null;
    }

}
