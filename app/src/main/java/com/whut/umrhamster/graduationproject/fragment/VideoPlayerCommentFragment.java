package com.whut.umrhamster.graduationproject.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.adapter.CommentAdapter;
import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.CommentPresenter;
import com.whut.umrhamster.graduationproject.presenter.ICommentPresenter;
import com.whut.umrhamster.graduationproject.utils.save.SPUtil;
import com.whut.umrhamster.graduationproject.view.ICommentView;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerCommentFragment extends Fragment implements IInitWidgetView,ICommentView {
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment> commentList;

    private SwipeRefreshLayout swipeRefreshLayout;
//    private EditText etMessage;
    private TextView tvMessage;
    private ImageView ivSend;

    private ICommentPresenter commentPresenter;

    private int start = 0;
    private boolean status = false;//判断是刷新还是加载 true刷新
    private boolean isLoading = false;
    private int lastItemPosition = 0;

    private int videoId;
    private Student student;

    private StringBuilder stringBuilder = new StringBuilder();

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fg_video_player_comment,container,false);
            initView(mView);
            initEvent();
        }
        return  mView;
//        View view = inflater.inflate(R.layout.fg_video_player_comment,container,false);
//        initView(view);
//        initEvent();
//        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                status = true;
                commentPresenter.doGetCommentsLimit10(videoId,0);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItemPosition = manager.findLastVisibleItemPosition();
                if (newState != RecyclerView.SCROLL_STATE_IDLE && !isLoading && lastItemPosition == commentList.size()-1){
                    isLoading = true;
                    commentPresenter.doGetCommentsLimit10(videoId,start);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mDialog = new Dialog(getActivity(),R.style.EditDialog);
                View view = View.inflate(getActivity(),R.layout.dialog_bottom_edit,null);
                mDialog.setContentView(view);

                final EditText et = mDialog.findViewById(R.id.dialog_edit_et);
                et.setHint("说点什么吧");
                et.setBackgroundResource(R.drawable.video_comment_et_bg);
                et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            if (student == null){
                                Toast.makeText(getActivity(),"登陆之后可进行评论",Toast.LENGTH_SHORT).show();
                                et.clearFocus();
                                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                manager.hideSoftInputFromWindow(mView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                    }
                });
                ImageView ivSend = mDialog.findViewById(R.id.dialog_edit_iv_send);
                ivSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (student == null){
                            Toast.makeText(getActivity(),"登陆之后可进行评论",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            if (!et.getText().toString().isEmpty()){
                                commentPresenter.doInsertComment(videoId,student.getId(),et.getText().toString());
                                et.setText("");
                            }else {
                                Toast.makeText(getActivity(),"评论内容不能为空",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                Window window = mDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        if (student != null){
                            et.setText(stringBuilder.toString());
                            et.setSelection(stringBuilder.length());
                            et.requestFocus();
                            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            manager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                });
                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        stringBuilder.replace(0,stringBuilder.length(),et.getText().toString());
                        et.clearFocus();
//                        et.setFocusable(false);
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(mView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                mDialog.show();
            }
        });
        //edittext焦点事件
//        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    if (student == null){
//                        Toast.makeText(getActivity(),"登录之后可进行聊天互动",Toast.LENGTH_SHORT).show();
//                        etMessage.clearFocus();
//                    }
//                }
//            }
//        });
//        ivSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (student == null){
//                    Toast.makeText(getActivity(),"登录之后可聊天互动",Toast.LENGTH_SHORT).show();
//                    return;
//                }else {
//                    if (etMessage.getText().toString().isEmpty()){
//                        Toast.makeText(getActivity(),"评论内容不能为空",Toast.LENGTH_SHORT).show();
//                    }else {
//                        commentPresenter.doInsertComment(videoId,student.getId(),etMessage.getText().toString());
//                    }
//                }
//            }
//        });
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.fg_video_player_comment_rv);
        swipeRefreshLayout = view.findViewById(R.id.fg_video_player_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.themeColor);
        tvMessage = view.findViewById(R.id.fg_video_player_et_message);
        ivSend = view.findViewById(R.id.fg_video_player_iv_send);
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData();

    }

    public void initData(){
        student = SPUtil.loadStudent(getActivity());
        videoId = getArguments().getInt("videoId");
        commentPresenter = new CommentPresenter(this);
        start = 0;
        commentPresenter.doGetCommentsLimit10(videoId,start);
    }

    @Override
    public void onGetCommentSuccess(List<Comment> commentList) {
        if (status){
            swipeRefreshLayout.setRefreshing(false);
            status = false;
            this.commentList.clear();
            start = 0;
        }
        isLoading = false;
        start = start+(commentList == null ?0:commentList.size());
        this.commentList.addAll(commentList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetCommentFail(int code) {
        if (code == 2000){
            start = 0;
            status = true;
            commentPresenter.doGetCommentsLimit10(videoId,0); //重新获取最新评论信息
            Toast.makeText(getActivity(),"评论成功",Toast.LENGTH_SHORT).show();
            return;
        }else if (code == -2){
            Toast.makeText(getActivity(),"评论失败",Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
        status = false;
        isLoading = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null){
            ((ViewGroup)mView.getParent()).removeView(mView);
        }
    }
}
