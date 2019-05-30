package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Comment;

import java.util.List;

public interface ICommentBiz{

    void getCommentsByVideoId(int videoId, OnCommentListener onCommentListener);

    void getCommentLimit10(int videoId,int start, OnCommentListener onCommentListener);

    void insertComment(int videoId,int studentId,String content,OnCommentListener onCommentListener);

    public interface OnCommentListener{
        void onSuccess(List<Comment> commentList);
        void onFail(int code);
    }
}
