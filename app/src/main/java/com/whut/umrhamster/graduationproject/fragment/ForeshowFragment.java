package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.ForeshowActivity;
import com.whut.umrhamster.graduationproject.ForeshowDecoration;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.ForeshowAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ForeshowPresenter;
import com.whut.umrhamster.graduationproject.presenter.IForeshowPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.IForeshowView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class ForeshowFragment extends Fragment implements IInitWidgetView,IForeshowView {
    private RecyclerView recyclerView;
    private List<Foreshow> foreshowList;
    private ForeshowAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private IForeshowPresenter foreshowPresenter;

    private int lastItemPosition = 0;
    private boolean isLoading = false;
    private boolean status = false;

    private int start = 0;

    //复用
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fg_foreshow,container,false);
            initView(mView);
            initEvent();
        }
        return mView;
//        View view = inflater.inflate(R.layout.fg_foreshow,container,false);
//        initView(view);
//        initEvent();
//        return view;
    }

    public void initData(){
        foreshowPresenter = new ForeshowPresenter(this);
        foreshowPresenter.doGetForeshowLimit10(0);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new ForeshowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),ForeshowActivity.class);
                intent.putExtra("foreshow",foreshowList.get(position));
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                status = true;
                isLoading = false;
                foreshowPresenter.doGetForeshowLimit10(0);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
                if (newState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == foreshowList.size()-1){
                    isLoading = true;
                    foreshowPresenter.doGetForeshowLimit10(start);
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
        swipeRefreshLayout = view.findViewById(R.id.fg_foreshow_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.themeColor);
        recyclerView = view.findViewById(R.id.foreshow_rv_show);
        foreshowList = new ArrayList<>();

        adapter = new ForeshowAdapter(foreshowList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new ForeshowDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onGetForeshowSuccess(List<Foreshow> foreshowList) {
        if (status){ //true-下拉刷新操作
            swipeRefreshLayout.setRefreshing(false);
            this.foreshowList.clear();
            status = false;
            start = 0;
        }
        isLoading = false;
        if (foreshowList == null || foreshowList.size() == 0){
            isLoading = true;
        }
//        Log.d("dddddd","ss"+start);
        start = start+(foreshowList == null ?0:foreshowList.size());
//        Log.d("dddddd","ssaaaaa"+start);
        this.foreshowList.addAll(foreshowList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetForeshowFail(int code) {
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
        status = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
    }
}
