package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.CollectAdapter;
import com.whut.umrhamster.graduationproject.adapter.SearchResultVideoAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultVideoFragment extends Fragment implements IInitWidgetView,IVideoView {
    private List<Video> videoList;
    private SearchResultVideoAdapter adapter;
    private RecyclerView recyclerView;

    private IVideoPresenter videoPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_search_result_video,container,false);
        Log.d("video",getArguments().getString("keyword"));
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
        videoPresenter = new VideoPresenter(this);
        recyclerView = view.findViewById(R.id.fg_search_result_video_rv);
        videoList = new ArrayList<>();
        adapter = new SearchResultVideoAdapter(videoList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        videoPresenter.doGetVideoByKeyWord(getArguments().getString("keyword"));
    }

    @Override
    public void onVideoSuccess(List<Video> videoList) {
        this.videoList.clear();
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
