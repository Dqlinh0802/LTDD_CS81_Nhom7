package com.example.btl_didong;


import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_didong.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.example.btl_didong.R.*;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        init();
        addTabs();

    }
    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(id.viewPager);
        tabLayout = findViewById(id.tabLayout);
    }

    private void addTabs(){
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_heart));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_heart_fill));


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(drawable.ic_home_fill);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_home_fill);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_search);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_add);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_heart_fill);
                        break;
                    case 4:
                        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_help);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_home);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_search);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_add);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_heart);
                        break;
                    case 4:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_heart_fill);
                        break;
                }            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_home_fill);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_search);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_add);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(drawable.ic_heart_fill);
                        break;
                    case 4:
                        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_help);
                        break;
                }
            }
        });
    }
}