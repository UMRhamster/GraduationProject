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

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.HistoryAdapter;
import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.presenter.HistoryPresenter;
import com.whut.umrhamster.graduationproject.presenter.IHistoryPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IHistoryView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryLiveFragment extends Fragment implements IInitWidgetView,IHistoryView {
    private List<History> historyList;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    private IHistoryPresenter historyPresenter;
    private int start;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_history_live,container,false);
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
        historyList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.history_live_rv);
        adapter = new HistoryAdapter(historyList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData(){
        start = 0;
        historyPresenter = new HistoryPresenter(this);
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            historyPresenter.doHistory(start,student.getId(),2);
        }
    }

    @Override
    public void onHistorySuccess(List<History> historyList) {
        start+=historyList.size();
        adapter.addAll(historyList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHistoryFail(int code) {

    }
}
