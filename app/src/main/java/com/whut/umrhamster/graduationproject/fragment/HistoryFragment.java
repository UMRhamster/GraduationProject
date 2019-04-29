package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.HistoryFragmentPagerAdapter;
import com.whut.umrhamster.graduationproject.interfaces.TextEditListener;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.widget.NoScrollViewPager;
import com.whut.umrhamster.graduationproject.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements IInitWidgetView,TextEditListener {
    private TextView tvEdit;

    private NoScrollViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbAll,rbVideo,rbLive;
    private HistoryFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;

    //底部工具栏
    private RelativeLayout relativeLayout;
    private SmoothCheckBox scb;
    private TextView tvAll;
    private TextView tvDelete;

//    private int lastItem;  //上一个fragment标志位,用于调用对应方法

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

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()){
                    case 0:
                        if (tvEdit.getText().toString().equals("编辑")){
                            ((HistoryAllFragment)fragmentList.get(0)).edit();
                            relativeLayout.setVisibility(View.VISIBLE);
                            tvEdit.setText("取消");
                        }else {
                            ((HistoryAllFragment)fragmentList.get(0)).cancelEdit();
                            relativeLayout.setVisibility(View.GONE);
                            tvEdit.setText("编辑");
                            scb.setChecked(false);
                        }
                        break;
                    case 1:
                        if (tvEdit.getText().toString().equals("编辑")){
                            ((HistoryVideoFragment)fragmentList.get(1)).edit();
                            relativeLayout.setVisibility(View.VISIBLE);
                            tvEdit.setText("取消");
                        }else {
                            ((HistoryVideoFragment)fragmentList.get(1)).cancelEdit();
                            relativeLayout.setVisibility(View.GONE);
                            tvEdit.setText("编辑");
                            scb.setChecked(false);
                        }
                        break;
                    case 2:
                        if (tvEdit.getText().toString().equals("编辑")){
                            ((HistoryLiveFragment)fragmentList.get(2)).edit();
                            relativeLayout.setVisibility(View.VISIBLE);
                            tvEdit.setText("取消");
                        }else {
                            ((HistoryLiveFragment)fragmentList.get(2)).cancelEdit();
                            relativeLayout.setVisibility(View.GONE);
                            tvEdit.setText("编辑");
                            scb.setChecked(false);
                        }
                        break;

                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvEdit.setText("编辑");
                relativeLayout.setVisibility(View.GONE);
                scb.setChecked(false);
                if (position == 0){
                    ((HistoryAllFragment)fragmentList.get(position)).cancelEdit();
                }else if (position == 1){
                    ((HistoryVideoFragment)fragmentList.get(position)).cancelEdit();
                }else if (position == 2){
                    ((HistoryLiveFragment)fragmentList.get(position)).cancelEdit();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        scb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomControl();
            }
        });
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomControl();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()){
                    case 0:
                        if (scb.isChecked()){
                            ((HistoryAllFragment)fragmentList.get(0)).deleteAll();
                        }else {
                            ((HistoryAllFragment)fragmentList.get(0)).delete();
                        }
                        break;
                    case 1:
                        if (scb.isChecked()){
                            ((HistoryVideoFragment)fragmentList.get(1)).deleteAll();
                        }else {
                            ((HistoryVideoFragment)fragmentList.get(1)).delete();
                        }
                        break;
                    case 2:
                        if (scb.isChecked()){
                            ((HistoryLiveFragment)fragmentList.get(2)).deleteAll();
                        }else {
                            ((HistoryLiveFragment)fragmentList.get(2)).delete();
                        }
                        break;
                }
//                Toast.makeText(getActivity(),"shanchu",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bottomControl(){
        if (scb.isChecked()){
            scb.setChecked(false);
        }else {
            scb.setChecked(true);
        }
        switch (viewPager.getCurrentItem()){
            case 0:
                if (scb.isChecked()){
                    ((HistoryAllFragment)fragmentList.get(0)).chooseAll();
                }else {
                    ((HistoryAllFragment)fragmentList.get(0)).cancelAll();
                }
                break;
            case 1:
                if (scb.isChecked()){
                    ((HistoryVideoFragment)fragmentList.get(1)).chooseAll();
                }else {
                    ((HistoryVideoFragment)fragmentList.get(1)).cancelAll();
                }
                break;
            case 2:
                if (scb.isChecked()){
                    ((HistoryLiveFragment)fragmentList.get(2)).chooseAll();
                }else {
                    ((HistoryLiveFragment)fragmentList.get(2)).cancelAll();
                }
                break;
        }
    }

    public void setTvEdit(String text){
        tvEdit.setText(text);
    }

    public void setScb(boolean checked){
        scb.setChecked(checked);
    }

    @Override
    public void initView(View view) {
        tvEdit = view.findViewById(R.id.history_tb_tv_edit);
        radioGroup = view.findViewById(R.id.history_rg);
        rbAll = view.findViewById(R.id.history_rg_rb_all);
        rbVideo = view.findViewById(R.id.history_rg_rb_video);
        rbLive = view.findViewById(R.id.history_rg_rb_live);
        viewPager = view.findViewById(R.id.history_vp);
        //
       relativeLayout = view.findViewById(R.id.history_ll_bottom);
        scb = view.findViewById(R.id.history_ll_bottom_scb);
        tvAll = view.findViewById(R.id.history_ll_bottom_tv_all);
        tvDelete = view.findViewById(R.id.history_ll_bottom_tv_delete);
        fragmentList = new ArrayList<>(3);
        HistoryAllFragment historyAllFragment = new HistoryAllFragment();
        historyAllFragment.setTextEditListener(this);
        HistoryVideoFragment historyVideoFragment = new HistoryVideoFragment();
        historyVideoFragment.setTextEditListener(this);
        HistoryLiveFragment historyLiveFragment = new HistoryLiveFragment();
        historyLiveFragment.setTextEditListener(this);
        fragmentList.add(historyAllFragment);
        fragmentList.add(historyVideoFragment);
        fragmentList.add(historyLiveFragment);
        adapter = new HistoryFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTextEdit(String text) {
        tvEdit.setText(text);
        relativeLayout.setVisibility(View.GONE);
        scb.setChecked(false);
    }

}
