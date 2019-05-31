package com.whut.umrhamster.graduationproject.presenter;

public interface IWatchPresenter extends BasePresenter {
    void doGetWatchByStudentId(int studentId);
    void doGetWatchLimit20(int studentId,int start);

    void isWatchExist(int studentId,int teacherId);
    void doAddWatch(int studentId,int teacherId);
    void doDeleteWatchById(int watchId);
    void doDeleteWatchBySaT(int studentId,int teacherId);

    void doGetNumOfWatch(int idf,int id);
}
