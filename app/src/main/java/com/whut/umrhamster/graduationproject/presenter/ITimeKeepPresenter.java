package com.whut.umrhamster.graduationproject.presenter;

public interface ITimeKeepPresenter extends BasePresenter{
    void doGetTimeKeepById(int studentId);

    void doUploadTimeKeep(int studentId,int typeId,int time);
}
