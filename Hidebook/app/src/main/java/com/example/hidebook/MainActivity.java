package com.example.hidebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.hidebook.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ImageButton settingBT;

    private ViewPager viewPager;

    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide the title bar
        getSupportActionBar().hide();
        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        //Add icon
        addTabs();
        //Test log out
        clickListener();

    }

    private void init(){
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        settingBT = findViewById(R.id.ib_setting);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

    }

    private void clickListener(){
        settingBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, FragmentReplacerActivity.class));
                    //Go back to home page
                    finish();
                }
            });
    }

    private void addTabs(){
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_notification));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search));
        //
        tabLayout.setTabGravity(tabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        //
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_selected);
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_notification_selected);
                    case 4:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_notification);
                    case 4:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_selected);
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_notification_selected);
                    case 4:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
                }
            }
        });
    }




}