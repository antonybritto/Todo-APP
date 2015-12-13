package com.flipkart.todo.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.flipkart.todo.R;
import com.flipkart.todo.TaskStatus;
import com.flipkart.todo.model.TaskTable;

import java.util.HashMap;

/**
 * Created by monish.kumar on 13/12/15.
 */
public class SettingsFragment extends Fragment {

    CheckedTextView deleteCompletedTasks;
    CheckedTextView clearCompletedTasks;
    CheckedTextView clearRecycleBin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.settings, container, false);
        deleteCompletedTasks = (CheckedTextView) fragmentView.findViewById(R.id.deleteCompletedTasks);
        clearCompletedTasks = (CheckedTextView) fragmentView.findViewById(R.id.clearCompletedTasks);
        clearRecycleBin = (CheckedTextView) fragmentView.findViewById(R.id.clearRecycleBin);
        clearRecycleBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.clearRecycleBin);
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
                                    TaskTable.delete(TaskTable.STATUS, TaskStatus.deleted.name());
                                    Toast toast = Toast.makeText(getContext(), "Cleared All Tasks", Toast.LENGTH_SHORT);
                                    toast.show();
                                    checkedTextView.setChecked(false);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    checkedTextView.setChecked(false);
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you want to Clear Trash").setPositiveButton("OK", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                }
            }
        });

        clearCompletedTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.clearCompletedTasks);
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
                                    TaskTable.delete(TaskTable.STATUS,TaskStatus.completed.name());
                                    Toast toast = Toast.makeText(getContext(), "Cleared All Tasks", Toast.LENGTH_SHORT);
                                    toast.show();
                                    checkedTextView.setChecked(false);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    checkedTextView.setChecked(false);
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you want to Clear Completed Tasks").setPositiveButton("OK", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                }
            }
        });

        deleteCompletedTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.deleteCompletedTasks);
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
                                    TaskTable.update(
                                            new HashMap<String, String>() {{
                                                put(TaskTable.STATUS, TaskStatus.completed.name());
                                            }},
                                            new HashMap<String, String>() {{
                                                put(TaskTable.STATUS, TaskStatus.deleted.name());
                                            }}
                                    );
                                    Toast toast = Toast.makeText(getContext(), "Deleted All Completed Tasks", Toast.LENGTH_SHORT);
                                    toast.show();
                                    checkedTextView.setChecked(false);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    checkedTextView.setChecked(false);
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you want to Delete Completed Tasks").setPositiveButton("OK", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                }
            }
        });



        return fragmentView;
    }
}
