package com.sgfootcal.android.footcal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serdar on 19.4.2017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager){

        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {


        return fragmentList.get(position);
    }


    @Override
    public int getCount() {


        return fragmentList.size();
    }
    public void addFragment(Fragment fragment , String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {


        return fragmentTitleList.get(position);
    }
}
