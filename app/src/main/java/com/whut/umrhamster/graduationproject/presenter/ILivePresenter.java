package com.whut.umrhamster.graduationproject.presenter;

public interface ILivePresenter extends BasePresenter{
    void getAllLive();
    void doGetTypeLive(int type);
    void doGetLiveByKeyword(String keyword);

    void doGetLiveLimit10(int start);

    void doUpdateLiveViewers(int liveId,int operation);
}
