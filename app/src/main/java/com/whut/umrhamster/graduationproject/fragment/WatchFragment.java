package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whut.umrhamster.graduationproject.MainActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.TeacherInfoActivity;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private int start = 0;
    private boolean status = false;
    private boolean isLoading = false;

    private int position2Delete=-1;
    private int lastItemPosition = 0;

    private Student student;

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
        student = SPUtil.loadStudent(getActivity());
        if (student != null){
            start = 0;
            watchPresenter.doGetWatchLimit20(student.getId(),start);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = true;
                start = 0;
                if (student != null){
                    watchPresenter.doGetWatchLimit20(student.getId(),0);
                }
            }
        });

        adapter.setOnItemClickListener(new WatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),TeacherInfoActivity.class);
                intent.putExtra("teacher",watchList.get(position).getTeacher());
                startActivity(intent);
            }

            @Override
            public void onWatchClick(int position) {
                position2Delete = position;
                watchPresenter.doDeleteWatchById(watchList.get(position).getId());
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
                if (newState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == watchList.size()-1){
                    isLoading = true;
                    watchPresenter.doGetWatchLimit20(student.getId(),start);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void initView(View view) {
        ivMenu = view.findViewById(R.id.fg_watch_iv_menu);
        recyclerView = view.findViewById(R.id.fg_watch_rv);
        swipeRefreshLayout = view.findViewById(R.id.fg_watch_srl);

        watchList = new ArrayList<>();
        adapter = new WatchAdapter(watchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onWatchSuccess(List<Watch> watchList) {
        if (status){
            swipeRefreshLayout.setRefreshing(false);
            this.watchList.clear();
            start=0;
            status = false;
        }
        isLoading = false;
        if (watchList == null || watchList.size() == 0) { //如果已经没有更多数据，就不在触发上拉加载
            isLoading = true;
        }
        this.watchList.addAll(watchList);
        start = start+(watchList == null?0:watchList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onWatchFail(int code) {
        swipeRefreshLayout.setRefreshing(false);
        status = false;
        isLoading = false;
    }

    @Override
    public void onWatchExist(boolean exist) {

    }

    @Override
    public void onAddWatchSuccess() {

    }

    @Override
    public void onDeleteWatchSuccess() {
        if (position2Delete != -1){
            watchList.remove(position2Delete);
            adapter.notifyItemRemoved(position2Delete);
            position2Delete = -1;
        }
    }

    @Override
    public void onGetNumStudents(List<Watch> watchList) {

    }

    @Override
    public void onGetNumTeachers(List<Watch> watchList) {

    }
}
