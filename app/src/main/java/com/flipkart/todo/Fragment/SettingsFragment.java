package com.flipkart.todo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;

import com.flipkart.todo.OrderBy;
import com.flipkart.todo.R;
import com.flipkart.todo.model.TaskTable;

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


        return fragmentView;
    }
}
