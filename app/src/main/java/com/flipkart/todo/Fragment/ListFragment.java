package com.flipkart.todo.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flipkart.todo.Activity.MainActivity;
import com.flipkart.todo.Activity.TaskDetailActivity;
import com.flipkart.todo.OrderBy;
import com.flipkart.todo.R;
import com.flipkart.todo.Task;
import com.flipkart.todo.TaskAdapter;
import com.flipkart.todo.TaskFragmentList;
import com.flipkart.todo.TaskStatus;
import com.flipkart.todo.model.TaskTable;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ListView taskList;
    TaskAdapter adapter;
    Spinner sortSpinner;
    HashMap<String, OrderBy> sortOrder = new HashMap<>();
    HashMap<String, String> attributeValirPair = new HashMap<>();

    public ListFragment() {

    }



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
        sortOrder.put(TaskTable.DUE_DATE, OrderBy.DESC);
        attributeValirPair.put(TaskTable.STATUS, TaskStatus.pending.name());
        final Integer total = TaskTable.getCount(attributeValirPair);
        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.taskListLayout);
        // if no tasks then show
        if(total == 0) {
            final TextView textView = new TextView(getContext());
            textView.setText(R.string.no_tasks_message);
            final RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            layout.addView(textView, params);
        }

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra("CurrentPosition", position);
                intent.putExtra("SORT_ATTR", adapter.getSortPriority().entrySet().iterator().next().getKey());
                intent.putExtra("SORT_ORDER_BY", adapter.getSortPriority().entrySet().iterator().next().getValue().name());
                intent.putExtra("STATUS", attributeValirPair.get(TaskTable.STATUS));
                startActivity(intent);
            }
        });


        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.getSortPriority().clear();

                switch(position){
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
               adapter.notifyDataSetInvalidated();adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new TaskAdapter(getContext(), sortOrder, attributeValirPair);
        taskList.setAdapter(adapter);
        registerForContextMenu(taskList);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.add(R.string.add_task_button_text);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity ma = (MainActivity) getActivity();
                ma.switchFragment(TaskFragmentList.AddFragment, null);
                return true;
            }
        });
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
            Task task = TaskTable.getTask(adapter.getItemId(selectedPos));
            task.setStatus(TaskStatus.deleted);
            TaskTable.update(task);
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getContext(), "Deleted Task", Toast.LENGTH_SHORT);
            toast.show();
        }
        if(item.getItemId() == R.id.taskEditMenu){
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int selectedPos = contextMenuInfo.position;
            MainActivity main = (MainActivity) getActivity();
            main.switchFragment(TaskFragmentList.EditTaskFragment, adapter.getItemId(selectedPos));
            //tasks.remove(selectedPos);
            //adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

}
