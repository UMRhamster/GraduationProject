package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.HistoryFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements IInitWidgetView {
    private NoScrollViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbAll,rbVideo,rbLive;
    private HistoryFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.history_rg_rb_all:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.history_rg_rb_video:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.history_rg_rb_live:
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        radioGroup = view.findViewById(R.id.history_rg);
        rbAll = view.findViewById(R.id.history_rg_rb_all);
        rbVideo = view.findViewById(R.id.history_rg_rb_video);
        rbLive = view.findViewById(R.id.history_rg_rb_live);
        viewPager = view.findViewById(R.id.history_vp);
        fragmentList = new ArrayList<>(3);
        fragmentList.add(new HistoryAllFragment());
        fragmentList.add(new HistoryVideoFragment());
        fragmentList.add(new HistoryLiveFragment());
        adapter = new HistoryFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
    }
}
