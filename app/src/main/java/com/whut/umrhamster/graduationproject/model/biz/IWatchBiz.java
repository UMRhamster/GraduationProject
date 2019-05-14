package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Watch;

import java.util.List;

public interface IWatchBiz {
    void getWatchByStudentId(int studentId, OnWatchListener onWatchListener);

    public interface OnWatchListener{
        void onSuccess(List<Watch> watchList);
        void onFail(int code);
    }
}
