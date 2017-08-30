package com.philong.foody.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.philong.foody.controller.fragment.FragmentAnGi;
import com.philong.foody.controller.fragment.FragmentODau;

/**
 * Created by Long on 8/30/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = new Fragment[]{ FragmentODau.newInstance(), FragmentAnGi.newInstance()};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
