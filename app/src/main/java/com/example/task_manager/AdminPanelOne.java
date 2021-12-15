package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AdminPanelOne extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_one);


        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        fragments=new ArrayList<>();
        fragments.add(new TaskList());
        fragments.add(new UserList());
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentAdapt fragmentAdapt=new FragmentAdapt(fragmentManager,getApplicationContext(),fragments);
        viewPager.setAdapter(fragmentAdapt);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Tasks");
        tabLayout.getTabAt(1).setText("Users");

    }
}