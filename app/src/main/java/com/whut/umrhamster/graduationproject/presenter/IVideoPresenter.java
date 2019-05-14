package com.whut.umrhamster.graduationproject.presenter;

public interface IVideoPresenter extends BasePresenter{
    void doGetVideoLimit10(int start);
    void doGetVideoByType(int type);
}
