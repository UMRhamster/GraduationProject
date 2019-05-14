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
import android.widget.ImageView;

import com.whut.umrhamster.graduationproject.MainActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.WatchAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.presenter.IWatchPresenter;
import com.whut.umrhamster.graduationproject.presenter.WatchPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.util.ArrayList;
import java.util.List;

public class WatchFragment extends Fragment implements IInitWidgetView,IWatchView {
    private ImageView ivMenu;
    private RecyclerView recyclerView;
    private WatchAdapter adapter;
    private List<Watch> watchList;


    private IWatchPresenter watchPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_watch,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initData(){
        watchPresenter = new WatchPresenter(this);
        Student student = SPUtil.loadStudent(getActivity());
        if (student != null){
            watchPresenter.doGetWatchByStudentId(student.getId());
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });
    }

    @Override
    public void initView(View view) {
        ivMenu = view.findViewById(R.id.fg_watch_iv_menu);
        recyclerView = view.findViewById(R.id.fg_watch_rv);

        watchList = new ArrayList<>();
        adapter = new WatchAdapter(watchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onWatchSuccess(List<Watch> watchList) {
        this.watchList.addAll(watchList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onWatchFail(int code) {

    }
}
