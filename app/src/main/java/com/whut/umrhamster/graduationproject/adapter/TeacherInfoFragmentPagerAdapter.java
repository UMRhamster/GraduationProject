package com.whut.umrhamster.graduationproject.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TeacherInfoFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titles = {"视频","评价"};

    public TeacherInfoFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
