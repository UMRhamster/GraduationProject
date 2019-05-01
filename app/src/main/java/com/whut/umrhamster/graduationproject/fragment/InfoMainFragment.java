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
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.InfoMainExpandedAdapter;
import com.whut.umrhamster.graduationproject.interfaces.InfoGetListener;
import com.whut.umrhamster.graduationproject.model.bean.InfoChildBean;
import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ITimeKeepPresenter;
import com.whut.umrhamster.graduationproject.presenter.TimeKeepPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;

import java.util.ArrayList;
import java.util.List;

public class InfoMainFragment extends Fragment implements IInitWidgetView,ITimeKeepView {
    private RecyclerView recyclerView;
    private InfoMainExpandedAdapter adapter;
    private List<InfoGroupBean> groupBeanList;
//    private List<InfoChildBean> childBeanList;

    private InfoGetListener infoGetListener;

    private ITimeKeepPresenter timeKeepPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_info_main,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void setInfoGetListener(InfoGetListener infoGetListener) {
        this.infoGetListener = infoGetListener;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_info_main_rv);
//        childBeanList = new ArrayList<>();

        groupBeanList = new ArrayList<>(3);
        adapter = new InfoMainExpandedAdapter(groupBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    public void initData(){
        timeKeepPresenter = new TimeKeepPresenter(this);
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            timeKeepPresenter.doGetTimeKeepById(student.getId());
        }
    }

    @Override
    public void onTimeKeepSuccess(List<InfoGroupBean> groupBeanList) {
        this.groupBeanList.clear();
        this.groupBeanList.addAll(groupBeanList);
        adapter.notifyDataSetChanged();
        int totalTime = 0;
        for (int i=0;i<groupBeanList.size();i++){
            totalTime+=groupBeanList.get(i).getTotalTime();
        }
        infoGetListener.onTotalTime(totalTime);
//        Toast.makeText(getActivity(),""+groupBeanList.size()+"ge",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeKeepFail(int code) {
        Toast.makeText(getActivity(),""+code,Toast.LENGTH_SHORT).show();
    }
}
