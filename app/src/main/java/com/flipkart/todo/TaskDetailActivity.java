package com.flipkart.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by monish.kumar on 12/12/15.
 */
public class TaskDetailActivity  extends AppCompatActivity {

    ViewPager viewPager;
    FragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_add);
//
//        Intent launchingIntent = getIntent();
//        countries = launchingIntent.getParcelableArrayListExtra("COUNTRIES");
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//
//
//        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                WikiFragment fragment = new WikiFragment();
//                String countryName = countries.get(position).name;
//                Bundle b = new Bundle();
//                b.putString("COUNTRY_NAME", countryName);
//                fragment.setArguments(b);
//
//                return fragment;
//            }
//
//            @Override
//            public int getCount() {
//                return countries.size();
//            }
//        };
//
//        viewPager.setAdapter(adapter);


    }
}
