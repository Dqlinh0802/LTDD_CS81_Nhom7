package com.example.hidebook;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.hidebook.adapter.ViewPagerAdapter;
import com.example.hidebook.fragment.Search;
import com.google.android.material.tabs.TabLayout;

public class  MainActivity extends AppCompatActivity implements Search.OnDataPass{

    private TabLayout tabLayout;


    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide the title bar
        //getSupportActionBar().hide();
        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        //Add icon
        addTabs();


    }

    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

    }


    private void addTabs(){
        //
        tabLayout.setSelectedTabIndicatorHeight(0);
        //
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_notification));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_notification_selected));
        //
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_selected);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:

                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);

                        break;
                    case 1:
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);

                        break;
                    case 3:
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        //tabLayout.getTabAt(3).setIcon(R.drawable.ic_notification_selected);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        //tabLayout.getTabAt(4).setIcon(R.drawable.ic_search);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#ABCBC3"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
                        break;
                    case 3:
                        //tabLayout.getTabAt(3).setIcon(R.drawable.ic_notification);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#ABCBC3"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_search);

                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        break;
                    case 1:

                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
                        break;
                    case 2:

                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFCC99"), PorterDuff.Mode.SRC_IN);
                        //tabLayout.getTabAt(3).setIcon(R.drawable.ic_notification_selected);
                        break;
                    case 4:

                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_search);

                        break;
                }

            }
        });
    }


    @Override
    public void onChange(String uid) {
        viewPager.setCurrentItem(Integer.parseInt(uid));
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 4)
            viewPager.setCurrentItem(0);
        else
            super.onBackPressed();
    }


}