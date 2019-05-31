package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Watch;

import java.util.List;

public interface IWatchBiz {
    void getWatchByStudentId(int studentId, OnWatchListener onWatchListener);

    void getWatchLimit20(int studentId,int start,OnWatchListener onWatchListener);

    void addWatch(int studentId,int teacherId,OnWatchListener onWatchListener);

    void isWatchExist(int studentId, int teacherId, OnWatchListener onWatchListener);

    void deleteWatchById(int id,OnWatchListener onWatchListener);

    void deleteWatchBySaT(int studentId, int teacherId, OnWatchListener onWatchListener);

    void getNumOfWatch(int idf, int id, OnWatchListener onWatchListener);

    public interface OnWatchListener{
        void onSuccess(List<Watch> watchList);
        void onFail(int code);
    }
}
