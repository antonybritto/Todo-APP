package com.flipkart.todo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.flipkart.todo.EditTaskFragment;
import com.flipkart.todo.OrderBy;
import com.flipkart.todo.R;
import com.flipkart.todo.Task;
import com.flipkart.todo.model.TaskTable;
import com.flipkart.todo.util.ToDoUtils;

import java.util.HashMap;

/**
 * Created by monish.kumar on 12/12/15.
 */
public class TaskDetailActivity  extends AppCompatActivity {

    String TAG = "TaskDetailActivity";
    ViewPager viewPager;
    FragmentStatePagerAdapter adapter;
    HashMap<String, OrderBy> sortOrder = new HashMap<>();
    HashMap<String, String> attributeValuePair = new HashMap<>();
    int currentPosition = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.task_detail);
//
        Intent launchingIntent = getIntent();



        viewPager = (ViewPager) findViewById(R.id.viewPager);

//
//
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                EditTaskFragment editTaskFragment = new EditTaskFragment();
                editTaskFragment.setIsDetailView(true);
                Task task = TaskTable.getTask(position, sortOrder, attributeValuePair);
                editTaskFragment.setTask(task);
                return editTaskFragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);

            }

            @Override
            public int getCount() {
                return TaskTable.getCount(attributeValuePair);
            }
        };
//
        viewPager.setAdapter(adapter);

        if (savedInstanceState == null) {
            sortOrder.put(launchingIntent.getStringExtra(ToDoUtils.SORT_ATTR),
                    OrderBy.valueOf(launchingIntent.getStringExtra(ToDoUtils.SORT_ORDER_BY)));
            attributeValuePair.put(TaskTable.STATUS, launchingIntent.getStringExtra(ToDoUtils.STATUS));
            currentPosition = launchingIntent.getIntExtra(ToDoUtils.CURRENT_POSITION, 0);
        } else {
            sortOrder.put(savedInstanceState.getString(ToDoUtils.SORT_ATTR),
                    OrderBy.valueOf(savedInstanceState.getString(ToDoUtils.SORT_ORDER_BY)));
            attributeValuePair.put(TaskTable.STATUS, savedInstanceState.getString(ToDoUtils.STATUS));
            currentPosition = savedInstanceState.getInt(ToDoUtils.CURRENT_POSITION, 0);
        }
        viewPager.setCurrentItem(currentPosition);

    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ToDoUtils.CURRENT_POSITION, currentPosition);
        outState.putString(ToDoUtils.SORT_ATTR, sortOrder.entrySet().iterator().next().getKey());
        outState.putString(ToDoUtils.SORT_ORDER_BY, sortOrder.entrySet().iterator().next().getValue().name());
        outState.putString(ToDoUtils.STATUS, attributeValuePair.get(TaskTable.STATUS));
    }
}
