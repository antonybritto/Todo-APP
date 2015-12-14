package com.flipkart.todo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.flipkart.todo.Activity.TaskDetailActivity;
import com.flipkart.todo.OrderBy;
import com.flipkart.todo.R;
import com.flipkart.todo.Task;
import com.flipkart.todo.TaskAdapter;
import com.flipkart.todo.TaskStatus;
import com.flipkart.todo.model.TaskTable;
import com.flipkart.todo.util.ToDoUtils;

import java.util.HashMap;

/**
 * Created by monish.kumar on 13/12/15.
 */
public class CompletedTaskFragment extends Fragment {

    ListView taskList;
    TaskAdapter adapter;
    Spinner sortSpinner;
    HashMap<String, OrderBy> sortOrder = new HashMap<>();
    HashMap<String, String> attributeValuePair = new HashMap<>();

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_list, container, false);
        taskList = (ListView) fragmentView.findViewById(R.id.listView);
        sortSpinner = (Spinner) fragmentView.findViewById(R.id.sortSpinner);
        if (savedInstanceState != null) {
            sortOrder.put(savedInstanceState.getString(ToDoUtils.SORT_ATTR), OrderBy.valueOf(savedInstanceState.getString(ToDoUtils.SORT_ORDER_BY)));
            attributeValuePair.put(TaskTable.STATUS, savedInstanceState.getString(ToDoUtils.STATUS));
        } else {
            sortOrder.put(TaskTable.DUE_DATE, OrderBy.DESC);
            attributeValuePair.put(TaskTable.STATUS, TaskStatus.completed.name());
        }
        final Integer total = TaskTable.getCount(attributeValuePair);
        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.taskListLayout);
        // if no tasks then show
        if(total == 0) {
            Toast toast = Toast.makeText(getContext(), "No Completed Tasks", Toast.LENGTH_SHORT);
            toast.show();
        }

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra(ToDoUtils.CURRENT_POSITION, position);
                intent.putExtra(ToDoUtils.SORT_ATTR, adapter.getSortPriority().entrySet().iterator().next().getKey());
                intent.putExtra(ToDoUtils.SORT_ORDER_BY, adapter.getSortPriority().entrySet().iterator().next().getValue().name());
                intent.putExtra(ToDoUtils.STATUS, attributeValuePair.get(TaskTable.STATUS));
                startActivity(intent);
            }
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null && adapter.getSortPriority() != null) {
                    adapter.getSortPriority().clear();

                    switch (position) {
                        case 0:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.DESC);
                            break;
                        case 1:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.ASC);
                            break;
                        case 2:
                            adapter.getSortPriority().put(TaskTable.DUE_DATE, OrderBy.DESC);
                            break;
                        case 3:
                            adapter.getSortPriority().put(TaskTable.PRIORITY, OrderBy.ASC);
                            break;
                        case 4:
                            adapter.getSortPriority().put(TaskTable.PRIORITY, OrderBy.DESC);
                            break;
                        case 5:
                            adapter.getSortPriority().put(TaskTable.TITLE, OrderBy.DESC);
                            break;
                    }
                    adapter.notifyDataSetInvalidated();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        ToDoUtils.setSpinnerOnClickListener(sortSpinner, adapter);
        adapter = new TaskAdapter(getContext(), sortOrder, attributeValuePair);
        adapter.setCheckBoxActionStatus(TaskStatus.pending);
        taskList.setAdapter(adapter);
        registerForContextMenu(taskList);
        return fragmentView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.complete_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.taskDeleteMenu){
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int selectedPos = contextMenuInfo.position;
            Task task = TaskTable.getTask(adapter.getItemId(selectedPos));
            task.setStatus(TaskStatus.deleted);
            TaskTable.update(task);
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getContext(), "Deleted Task", Toast.LENGTH_SHORT);
            toast.show();
        }
        if(item.getItemId() == R.id.taskPendingMenu){
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int selectedPos = contextMenuInfo.position;
            Task task = TaskTable.getTask(adapter.getItemId(selectedPos));
            task.setStatus(TaskStatus.pending);
            TaskTable.update(task);
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getContext(), "Moved To Pending Task", Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            outState.putString(ToDoUtils.SORT_ATTR, adapter.getSortPriority().entrySet().iterator().next().getKey());
            outState.putString(ToDoUtils.SORT_ORDER_BY, adapter.getSortPriority().entrySet().iterator().next().getValue().name());
            outState.putString(ToDoUtils.STATUS, attributeValuePair.get(TaskTable.STATUS));
        }
    }
}
