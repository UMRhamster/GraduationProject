package com.whut.umrhamster.graduationproject.presenter;

public interface ITimeKeepPresenter extends BasePresenter{
    void doGetTimeKeepById(int studentId,boolean onlyTime);

    void doUploadTimeKeep(int studentId,int typeId,int time);
}
