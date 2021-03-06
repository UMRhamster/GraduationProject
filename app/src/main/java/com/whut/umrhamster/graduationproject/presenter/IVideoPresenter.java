package com.whut.umrhamster.graduationproject.presenter;

public interface IVideoPresenter extends BasePresenter{
    void doGetVideoLimit10(int start);
    void doGetVideoByType(int type);
    void doGetVideoByTeacher(int teacherId);
    void doGetVideoByKeyWord(String keyword);

    void doAddNumOfPlay(int videoId);
}
