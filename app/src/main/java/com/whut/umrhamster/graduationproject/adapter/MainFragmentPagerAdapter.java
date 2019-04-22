package com.whut.umrhamster.graduationproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> fragmentList;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, String[] titles){
        super(fragmentManager);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
