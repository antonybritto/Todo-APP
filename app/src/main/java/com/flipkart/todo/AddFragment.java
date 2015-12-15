package com.flipkart.todo;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.flipkart.todo.util.ToDoUtils;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    EditText title = null;
    String TAG = "AddFragment";
    EditText notes = null;
    Spinner priority = null;
    Button addBtn;
    Button cancelBtn;
    Button deleteBtn;
    EditText dueDate;
    EditText dueTime;
    Calendar dueDateTime = Calendar.getInstance();
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    private int pYear;
    private int pMonth;
    private int pDay;

    private int pHour;
    private int pMin;

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
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

        dueDateTime.set(Calendar.DAY_OF_MONTH, pDay);
        dueDateTime.set(Calendar.MONTH, pMonth);
        dueDateTime.set(Calendar.YEAR, pYear);
    }

    private void updateTimeDisplay() {
        dueTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pHour).append(":")
                        .append(pMin).append(" "));

        dueDateTime.set(Calendar.HOUR_OF_DAY, pHour);
        dueDateTime.set(Calendar.MINUTE ,pMin);
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
        cancelBtn = (Button) fragmentview.findViewById(R.id.cancel);
        title = (EditText) fragmentview.findViewById(R.id.title);
        notes = (EditText) fragmentview.findViewById(R.id.notes);
        priority = (Spinner) fragmentview.findViewById(R.id.priority);
        dueDate = (EditText) fragmentview.findViewById(R.id.dueDate);
        dueTime = (EditText) fragmentview.findViewById(R.id.dueTime);

        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        Log.i(TAG, String.valueOf(cal.get(Calendar.YEAR)));
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);


        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMin = cal.get(Calendar.MINUTE);



        /** Display the current date in the TextView */
        dueDateTime = Calendar.getInstance();
        updateDisplay();
        updateTimeDisplay();




        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = title.getText().toString();
                String taskNotes = notes.getText().toString();
                Task task = new Task();
                task.title = taskTitle;
                task.notes = taskNotes;
                task.priority = priority.getSelectedItem().toString();
//                task.dueDate = dueDate.getText().toString();
                task.status = TaskStatus.pending;
                task.dueDate = dueDateTime.getTime();
                TaskTable.insert(task);
                Toast toast = Toast.makeText(getContext(), "Added New Task", Toast.LENGTH_SHORT);
                toast.show();
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
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
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
