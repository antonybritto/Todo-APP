package com.flipkart.todo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements AddTaskDelegate {
    Button button = null;
    ListView taskList;
    TaskAdapter adapter;
    ArrayList<Task> tasks = new ArrayList<Task>();

    public ListFragment() {

    }

    public void addTask(Task c){
        tasks.add(c);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_list, container, false);
        taskList = (ListView) fragmentView.findViewById(R.id.listView);
        adapter = new TaskAdapter(getContext(), tasks);
        taskList.setAdapter(adapter);
        registerForContextMenu(taskList);

        button = (Button) fragmentView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.switchToAddFragment();
            }
        });


        return fragmentView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.taskDeleteMenu){
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int selectedPos = contextMenuInfo.position;
            tasks.remove(selectedPos);
            adapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.taskEditMenu){
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int selectedPos = contextMenuInfo.position;
            MainActivity main = (MainActivity) getActivity();
            main.switchToAddFragment();
            //tasks.remove(selectedPos);
            //adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

}
