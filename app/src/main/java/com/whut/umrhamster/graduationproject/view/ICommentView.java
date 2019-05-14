package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Comment;

import java.util.List;

public interface ICommentView {
    void onGetCommentSuccess(List<Comment> commentList);
    void onGetCommentFail(int code);
}
