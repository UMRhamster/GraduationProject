package com.whut.umrhamster.graduationproject.presenter;

public interface ICommentPresenter extends BasePresenter{
    void doGetComments(int videoId);
    void doGetCommentsLimit10(int videoId,int start);

    void doInsertComment(int videoId,int studentId,String content);
}
