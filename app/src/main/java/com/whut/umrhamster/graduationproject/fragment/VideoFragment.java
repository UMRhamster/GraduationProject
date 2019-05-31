package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.GridDecoration;
import com.whut.umrhamster.graduationproject.PlayerActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.VideoPlayerActivity;
import com.whut.umrhamster.graduationproject.adapter.VideoAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment implements IInitWidgetView,IVideoView {
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<Video> videoList;

    private IVideoPresenter videoPresenter;
    private int start = 0;
    private boolean isLoading= false;
    //
    private int lastItemPosition = 0;
    private boolean status = false;//用于区分是刷新还是加载 false-加载,true-刷新

    //复用，保存状态
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        if (mView == null){
            mView = inflater.inflate(R.layout.fg_video,container,false);
            initView(mView);
            initEvent();
        }
//        View view = inflater.inflate(R.layout.fg_video,container,false);
//        initView(view);
//        initEvent();
        return mView;
    }

    public void initData(){
        videoPresenter = new VideoPresenter(this);
        videoPresenter.doGetVideoLimit10(start);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),VideoPlayerActivity.class);
                intent.putExtra("video",videoList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
                if (newState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == videoList.size()-1){
                    isLoading = true;
                    videoPresenter.doGetVideoLimit10(start);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                videoPresenter.doGetVideoLimit10(0); //下拉刷新从0开始重新获取
                status = true;//表示这是刷新动作
                isLoading = false;
            }
        });
    }

    @Override
    public void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.fg_video_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.themeColor);
        recyclerView = view.findViewById(R.id.fg_video_rv);
        videoList = new ArrayList<>();
        adapter = new VideoAdapter(videoList,getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDecoration(getActivity()));
        initData();
    }

    @Override
    public void onVideoSuccess(List<Video> videoList) {
        if (status){
            swipeRefreshLayout.setRefreshing(false);
            this.videoList.clear();
            status = false;
            start=0;
        }
        isLoading = false;
        if (videoList == null || videoList.size() == 0){
            isLoading = true;
            return;
        }
        this.videoList.addAll(videoList);
        start = start+(videoList == null ? 0:videoList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onVideoFail(int code) {
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
        status = false;
    }

    @Override
    public void onVideoTypeSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoTeacherSuccess(List<Video> videoList) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
    }
}
