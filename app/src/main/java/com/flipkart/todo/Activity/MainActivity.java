package com.flipkart.todo.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flipkart.todo.AddFragment;
import com.flipkart.todo.Fragment.CompletedTaskFragment;
import com.flipkart.todo.EditTaskFragment;
import com.flipkart.todo.Fragment.ListFragment;
import com.flipkart.todo.Fragment.RecycleBinFragment;
import com.flipkart.todo.Fragment.SettingsFragment;
import com.flipkart.todo.R;
import com.flipkart.todo.TaskFragmentList;
import com.flipkart.todo.model.TaskTable;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            loadListFragment();
        }
    }

    private void loadListFragment() {
        FragmentManager manager = getSupportFragmentManager();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(TaskFragmentList.ListFragment.name());
        if(fragment == null) {
            fragment = new ListFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.mainLayout, fragment, TaskFragmentList.ListFragment.name());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.completedTaskMenu){
            switchFragment(TaskFragmentList.CompletedTaskFragment, null);
        }

        if (item.getItemId() == R.id.trash){
            switchFragment(TaskFragmentList.RecycleBinFragment, null);
        }
        if (item.getItemId() == R.id.settings){
            switchFragment(TaskFragmentList.SettingsFragment, null);
        }

        if(item.getItemId() == R.id.home){
            switchFragment(TaskFragmentList.ListFragment, null);
        }

        return super.onOptionsItemSelected(item);
    }
    public void switchFragment(TaskFragmentList taskFragmentList, Long itemId) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = null;
        if(manager.getBackStackEntryCount()>0){
            FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1);
            String str=backEntry.getName();
            frag= manager.findFragmentByTag(str);
        } else {
            frag = (ListFragment) manager.findFragmentByTag(TaskFragmentList.ListFragment.name());
        }

        if(frag != null) {
            Log.i("test", "switch");
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(frag);
            switch (taskFragmentList){
                case AddFragment:
                    //trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    trans.add(R.id.mainLayout, new AddFragment(), taskFragmentList.name());
                    break;
                case EditTaskFragment:
                    EditTaskFragment editTaskFragment = new EditTaskFragment();
                    editTaskFragment.setTask(TaskTable.getTask(itemId));
                    //trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    trans.add(R.id.mainLayout, editTaskFragment, taskFragmentList.name());
                    break;
                case CompletedTaskFragment:
                    trans.add(R.id.mainLayout, new CompletedTaskFragment(), taskFragmentList.name());
                    break;
                case RecycleBinFragment:
                    trans.add(R.id.mainLayout, new RecycleBinFragment(), taskFragmentList.name());
                    break;
                case SettingsFragment:
                    trans.add(R.id.mainLayout, new SettingsFragment(), taskFragmentList.name());
                    break;
                case ListFragment:
                    trans.add(R.id.mainLayout, new ListFragment(), taskFragmentList.name());
                    break;
            }
            trans.addToBackStack(taskFragmentList.name());
            trans.commit();
        } else {
            Log.i(TAG, "fragment is null");
            loadListFragment();
        }
    }

    private void moveToTopPage(){
        while(getSupportFragmentManager().popBackStackImmediate()){

        }
    }
}
