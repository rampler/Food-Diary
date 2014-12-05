package com.example.medicineApp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.medicineApp.fragments.UserMealsFragment;
import com.example.medicineApp.fragments.UserProfileFragment;
import com.example.medicineApp.fragments.UserTrainingsFragment;

/**
 * Created by Sabina on 2014-12-05.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new UserProfileFragment();
            case 1:
                return new UserMealsFragment();
            case 2:
                return new UserTrainingsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}