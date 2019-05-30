package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.model.biz.CommentBiz;
import com.whut.umrhamster.graduationproject.model.biz.ICommentBiz;
import com.whut.umrhamster.graduationproject.view.ICommentView;

import java.util.List;

public class CommentPresenter implements ICommentPresenter {
    private ICommentView commentView;
    private ICommentBiz commentBiz;
    private Handler handler;

    public CommentPresenter(ICommentView commentView){
        this.commentView = commentView;
        commentBiz = new CommentBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetComments(int videoId) {
        commentBiz.getCommentsByVideoId(videoId, new ICommentBiz.OnCommentListener() {
            @Override
            public void onSuccess(final List<Comment> commentList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentView.onGetCommentSuccess(commentList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentView.onGetCommentFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetCommentsLimit10(int videoId, int start) {
        commentBiz.getCommentLimit10(videoId, start, new ICommentBiz.OnCommentListener() {
            @Override
            public void onSuccess(final List<Comment> commentList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentView.onGetCommentSuccess(commentList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentView.onGetCommentFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doInsertComment(int videoId, int studentId, String content) {
        commentBiz.insertComment(videoId, studentId, content, new ICommentBiz.OnCommentListener() {
            @Override
            public void onSuccess(List<Comment> commentList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //biz中并没有回调该方法
//                        commentView.onGetCommentFail();
                    }
                });
            }

            @Override
            public void onFail(int code) {
                commentView.onGetCommentFail(code);
            }
        });
    }

    @Override
    public void onDestroy() {
        commentView =null;
    }
}
