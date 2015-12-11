package com.flipkart.todo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    EditText title = null;
    EditText notes = null;
    Spinner priority = null;
    Button addBtn;
    Button cancelBtn;

    AddTaskDelegate delegate;

    public AddFragment() {
        // Required empty public constructor
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
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String taskTitle = title.getText().toString();
                String taskNotes = notes.getText().toString();
                Task task = new Task();
                task.title = taskTitle;
                task.notes = taskNotes;
                task.priority = priority.getSelectedItem().toString();
                task.status = TaskStatus.pending;
                TaskTable.insert(task);
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
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


}
