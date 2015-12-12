package com.flipkart.todo;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public void addTask(){

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
        ListFragment fragment = (ListFragment) manager.findFragmentByTag("LF");
        if(fragment == null) {
            fragment = new ListFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.mainLayout, fragment, "LF");
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

        }
        return super.onOptionsItemSelected(item);
    }
    public void switchFragment(TaskFragmentList taskFragmentList, Long itemId) {
        FragmentManager manager = getSupportFragmentManager();
        ListFragment frag = (ListFragment) manager.findFragmentByTag("LF");
        Log.i("test", "switch");
        if(frag != null) {
            Log.i("test", "switch");
            FragmentTransaction trans = manager.beginTransaction();
            switch (taskFragmentList){
                case AddFragment:
                    AddFragment addFrag = new AddFragment();
                    //trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    trans.remove(frag);
                    trans.add(R.id.mainLayout, addFrag, taskFragmentList.name());
                    trans.addToBackStack("ADD_" + taskFragmentList.name());

                    break;
                case EditTaskFragment:
                    EditTaskFragment editTaskFragment = new EditTaskFragment();
                    editTaskFragment.setTask(TaskTable.getTask(itemId));
                    //trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    trans.remove(frag);
                    trans.add(R.id.mainLayout, editTaskFragment, taskFragmentList.name());
                    trans.addToBackStack("ADD_"+ taskFragmentList.name());
                    break;
                case CompletedTaskFragment:
                    break;
                case RecycleBinFragment:
                    break;
                case SettingsFragment:
                    break;
            }

            trans.commit();
        }
    }
}
