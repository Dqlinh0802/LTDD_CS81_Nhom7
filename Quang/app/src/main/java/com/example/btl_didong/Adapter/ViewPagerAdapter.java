package com.example.btl_didong.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl_didong.fragments.Add;
import com.example.btl_didong.fragments.Home;
import com.example.btl_didong.fragments.Notification;
import com.example.btl_didong.fragments.Profile;
import com.example.btl_didong.fragments.Search;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Home();
            case 1:
                return new Search();
            case 2:
                return new Add();
            case 3:
                return new Notification();
            case 4 :
                return new Profile();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
