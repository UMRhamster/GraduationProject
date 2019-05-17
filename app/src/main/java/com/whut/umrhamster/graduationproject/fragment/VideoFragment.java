package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<Video> videoList;

    private IVideoPresenter videoPresenter;
    private int start = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_video,container,false);
        initView(view);
        initEvent();
        return view;
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
    }

    @Override
    public void initView(View view) {
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
        this.videoList.addAll(videoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onVideoFail(int code) {

    }

    @Override
    public void onVideoTypeSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoTeacherSuccess(List<Video> videoList) {

    }
}
