package com.flipkart.todo;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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


    public void switchToAddFragment() {
        FragmentManager manager = getSupportFragmentManager();
        ListFragment frag = (ListFragment) manager.findFragmentByTag("LF");
        Log.i("test", "switch");
        if(frag != null) {
            Log.i("test", "switch");
            FragmentTransaction trans = manager.beginTransaction();
            AddFragment addFrag = new AddFragment();
            addFrag.delegate = frag;
            //trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            trans.remove(frag);
            trans.add(R.id.mainLayout, addFrag, "AF");
            trans.addToBackStack("ADD");
            trans.commit();
        }
    }

}
