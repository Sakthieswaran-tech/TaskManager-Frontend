package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class EmployeePanel extends AppCompatActivity {
    TabLayout tabLayout2;
    ViewPager viewPager2;
    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel);

        tabLayout2=findViewById(R.id.tablayout2);
        viewPager2=findViewById(R.id.viewpager2);
        fragments=new ArrayList<>();

        fragments.add(new TodayTask());
        fragments.add(new TaskList());
        fragments.add(new InCompleteTaskList());
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentAdapt fragmentAdapt=new FragmentAdapt(fragmentManager,getApplicationContext(),fragments);
        viewPager2.setAdapter(fragmentAdapt);

        tabLayout2.setupWithViewPager(viewPager2);
        tabLayout2.getTabAt(0).setText("Today");
        tabLayout2.getTabAt(1).setText("Complete");
        tabLayout2.getTabAt(2).setText("InComplete");

    }
}