package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
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
import com.whut.umrhamster.graduationproject.VideoPlayerActivity;
import com.whut.umrhamster.graduationproject.adapter.TeacherVideoAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.presenter.IVideoPresenter;
import com.whut.umrhamster.graduationproject.presenter.VideoPresenter;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.util.ArrayList;
import java.util.List;

public class TeacherVideoFragment extends Fragment implements IInitWidgetView,IVideoView {
    private RecyclerView recyclerView;
    private TeacherVideoAdapter adapter;
    private List<Video> videoList;

    private IVideoPresenter videoPresenter;

    private Teacher teacher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AdaptionUtil.setCustomDensity(getActivity(),getActivity().getApplication());
        View view = inflater.inflate(R.layout.fg_teacher_video,container,false);
        initView(view);
        initEvent();
        return view;
    }

    private void initData(){
        teacher = getArguments().getParcelable("teacher");
        videoPresenter = new VideoPresenter(this);
        videoPresenter.doGetVideoByTeacher(teacher.getId());
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new TeacherVideoAdapter.OnItemClickListener() {
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
        recyclerView = view.findViewById(R.id.fg_teacher_video_rv);
        videoList = new ArrayList<>();
        adapter = new TeacherVideoAdapter(videoList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onVideoSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoFail(int code) {

    }

    @Override
    public void onVideoTypeSuccess(List<Video> videoList) {

    }

    @Override
    public void onVideoTeacherSuccess(List<Video> videoList) {
        this.videoList.addAll(videoList);
        adapter.notifyDataSetChanged();
    }
}
