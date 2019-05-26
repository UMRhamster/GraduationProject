package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.SearchResultFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements IInitWidgetView {
    private List<Fragment> fragmentList;
    private SearchResultFragmentPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_search_result,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView(View view) {
        tabLayout = view.findViewById(R.id.fg_result_tb);
        viewPager = view.findViewById(R.id.fg_result_vp);
        fragmentList = new ArrayList<>(3);
        Bundle bundle = new Bundle();
        bundle.putString("keyword",getArguments().getString("keyword"));
        SearchResultVideoFragment videoFragment = new SearchResultVideoFragment();
        videoFragment.setArguments(bundle);
        SearchResultLiveFragment liveFragment = new SearchResultLiveFragment();
        liveFragment.setArguments(bundle);
        SearchResultTeacherFragment teacherFragment = new SearchResultTeacherFragment();
        teacherFragment.setArguments(bundle);
        fragmentList.add(videoFragment);
        fragmentList.add(liveFragment);
        fragmentList.add(teacherFragment);
        adapter = new SearchResultFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void doSearch(String keyword){
        Log.d("keyword",getArguments().getString("keyword"));
//        Bundle bundle = new Bundle();
//        bundle.putString("keyword",keyword);
//        SearchResultVideoFragment videoFragment = new SearchResultVideoFragment();
//        videoFragment.setArguments(bundle);
//        SearchResultLiveFragment liveFragment = new SearchResultLiveFragment();
//        liveFragment.setArguments(bundle);
//        SearchResultTeacherFragment teacherFragment = new SearchResultTeacherFragment();
//        teacherFragment.setArguments(bundle);
//        Log.d("test","cear");
//        fragmentList.clear();
//        fragmentList.add(videoFragment);
//        fragmentList.add(liveFragment);
//        fragmentList.add(teacherFragment);
//        adapter.notifyDataSetChanged();
    }

}
