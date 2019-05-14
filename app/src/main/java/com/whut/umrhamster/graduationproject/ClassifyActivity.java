package com.whut.umrhamster.graduationproject;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.whut.umrhamster.graduationproject.adapter.ClassifyFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.fragment.ClassifyLiveFragment;
import com.whut.umrhamster.graduationproject.fragment.ClassifyVideoFragment;
import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends AppCompatActivity implements IInitWidgetView {
    //分类数据
    private Classify classify;
    //widget
    private ImageView ivBack;
    private TextView tvTitle;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private ClassifyFragmentPagerAdapter adapter;

    private String[] titles = {"视频","直播"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        classify = getIntent().getParcelableExtra("classify");
        ivBack = findViewById(R.id.ac_classify_iv_back);
        tvTitle = findViewById(R.id.ac_classify_tb_tv_title);
        tabLayout = findViewById(R.id.ac_classify_tb);
        viewPager = findViewById(R.id.ac_classify_vp);
        fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("classify",classify);
        ClassifyVideoFragment classifyVideoFragment = new ClassifyVideoFragment();
        classifyVideoFragment.setArguments(bundle);
        ClassifyLiveFragment classifyLiveFragment = new ClassifyLiveFragment();
        classifyLiveFragment.setArguments(bundle);
        fragmentList.add(classifyVideoFragment);
        fragmentList.add(classifyLiveFragment);
        adapter = new ClassifyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager,titles);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView(View view) {

    }
}
