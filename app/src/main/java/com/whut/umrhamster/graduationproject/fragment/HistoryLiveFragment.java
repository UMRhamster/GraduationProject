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
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryLiveFragment extends Fragment implements IInitWidgetView {
    private List<Object> liveList;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
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
        liveList = new ArrayList<>();
        for (int i=0;i<10;i++){
            liveList.add(new Live("直播标题",Calendar.getInstance(),""));
        }

        recyclerView = view.findViewById(R.id.history_live_rv);
        adapter = new HistoryAdapter(liveList,HistoryAdapter.LIVE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
