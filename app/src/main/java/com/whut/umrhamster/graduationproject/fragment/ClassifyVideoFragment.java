package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
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
import com.whut.umrhamster.graduationproject.VideoPlayerActivity;
import com.whut.umrhamster.graduationproject.adapter.ClassifyVideoAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.util.ArrayList;
import java.util.List;

public class ClassifyVideoFragment extends Fragment implements IInitWidgetView,IVideoView {
    private RecyclerView recyclerView;
    private ClassifyVideoAdapter adapter;
    private List<Video> videoList;

    //sj
    private Classify classify;
    private IVideoPresenter videoPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_classify_video,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new ClassifyVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),VideoPlayerActivity.class);
                intent.putExtra("video",videoList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_classify_video_rv);
        videoList = new ArrayList<>();
        adapter = new ClassifyVideoAdapter(videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    public void initData(){
        classify = getArguments().getParcelable("classify");
        videoPresenter = new VideoPresenter(this);
        videoPresenter.doGetVideoByType(classify.getId());
        Log.d("classifyvideo",""+classify.getId());
    }

    @Override
    public void onVideoSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoFail(int code) {

    }

    @Override
    public void onVideoTypeSuccess(List<Video> videoList) {
        this.videoList.addAll(videoList);
        adapter.notifyDataSetChanged();
    }
}
