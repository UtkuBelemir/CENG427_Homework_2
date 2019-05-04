package com.ubelemir.homework2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public FragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            FoodListFragment foodListFragment = new FoodListFragment();
            return foodListFragment;
        }
        if (i == 1){
            CENGFragment cengFragment = new CENGFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ContentType","news");
            cengFragment.setArguments(bundle);
            return cengFragment;
        } else {
            CENGFragment cengFragment = new CENGFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ContentType","announcements");
            cengFragment.setArguments(bundle);
            return cengFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Menu";
            case 1:
                return "News";
            case 2:
                return "Announcements";
            default:
                return "";
        }
    }
}
