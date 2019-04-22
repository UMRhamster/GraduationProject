package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.ForeshowDecoration;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.ForeshowAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ForeshowFragment extends Fragment implements IInitWidgetView {
    private RecyclerView recyclerView;
    private List<Foreshow> foreshowList;
    private ForeshowAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_foreshow,container,false);
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
        recyclerView = view.findViewById(R.id.foreshow_rv_show);
        foreshowList = new ArrayList<>();
        foreshowList.add(new Foreshow("https://10.url.cn/qqcourse_logo_ng/ajNVdqHZLLDIIBJsbticBRR1xWbns2oFibLjdRgHUeicNeDO5hfJx9icXFvLaI9hIGsmhEQU2xyA124/356?tp=webp",R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
//        foreshowList.add(new Foreshow(R.mipmap.test_itembg,R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));
        foreshowList.add(new Foreshow("http://192.168.1.106:8088/356.webp",R.drawable.icon_user_blackwidow,"张老师","高数教学直播间"));

        adapter = new ForeshowAdapter(foreshowList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ForeshowDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
