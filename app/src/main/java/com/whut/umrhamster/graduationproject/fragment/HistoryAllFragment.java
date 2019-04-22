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
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryAllFragment extends Fragment implements IInitWidgetView {
    private List<Object> objectList;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_history_all,container,false);
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
        objectList = new ArrayList<>();
        for (int i =0;i<10;i++){
            objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),Calendar.getInstance(),923,374));
        }
        Calendar calendar = Calendar.getInstance();
//        Log.d("test",calendar.get(Calendar.MONTH)+" "+calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        calendar.add(Calendar.HOUR_OF_DAY,3);
//        Log.d("test",calendar.get(Calendar.MONTH)+" "+calendar.get(Calendar.DAY_OF_MONTH));
//        calendar.add(Calendar.HOUR_OF_DAY,-20);
//        calendar.add(Calendar.MINUTE,-4);
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar,1000,454));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar,900,454));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar,1000,784));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar,1000,954));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar,100,24));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH,-4);
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar1,1000,454));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar1,900,454));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar1,1000,784));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar1,1000,954));
        objectList.add(new Video("标题1标题1",new Teacher(0,"张老师"),calendar1,100,24));

        for (int i=0;i<10;i++){
            objectList.add(new Live("直播标题",Calendar.getInstance(),""));
        }
        recyclerView = view.findViewById(R.id.history_all_rv);
        adapter = new HistoryAdapter(objectList,HistoryAdapter.ALL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
