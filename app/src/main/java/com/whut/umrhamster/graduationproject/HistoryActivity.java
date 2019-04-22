package com.whut.umrhamster.graduationproject;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.whut.umrhamster.graduationproject.adapter.HistoryFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.HistoryAllFragment;
import com.whut.umrhamster.graduationproject.fragment.HistoryLiveFragment;
import com.whut.umrhamster.graduationproject.fragment.HistoryVideoFragment;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements IInitWidgetView {
    private NoScrollViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbAll,rbVideo,rbLive;
    private HistoryFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();
        initEvent();
    }

    @Override
    public void initView() {
        radioGroup = findViewById(R.id.history_rg);
        rbAll = findViewById(R.id.history_rg_rb_all);
        rbVideo = findViewById(R.id.history_rg_rb_video);
        rbLive = findViewById(R.id.history_rg_rb_live);
        viewPager = findViewById(R.id.history_vp);
        fragmentList = new ArrayList<>(3);
        fragmentList.add(new HistoryAllFragment());
        fragmentList.add(new HistoryVideoFragment());
        fragmentList.add(new HistoryLiveFragment());
        adapter = new HistoryFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
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

    }
}
