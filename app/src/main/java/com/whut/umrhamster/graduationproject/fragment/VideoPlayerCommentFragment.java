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
import com.whut.umrhamster.graduationproject.adapter.CommentAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.presenter.CommentPresenter;
import com.whut.umrhamster.graduationproject.presenter.ICommentPresenter;
import com.whut.umrhamster.graduationproject.view.ICommentView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerCommentFragment extends Fragment implements IInitWidgetView,ICommentView {
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment> commentList;

    private ICommentPresenter commentPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_video_player_comment,container,false);
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
        recyclerView = view.findViewById(R.id.fg_video_player_comment_rv);
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();

    }

    public void initData(){
        commentPresenter = new CommentPresenter(this);
        int videoId = getArguments().getInt("videoId");
        commentPresenter.doGetComments(videoId);
    }

    @Override
    public void onGetCommentSuccess(List<Comment> commentList) {
        Log.d("dsa",""+commentList.size());
        this.commentList.addAll(commentList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCommentFail(int code) {
        Log.d("dsa",""+code);
    }
}
