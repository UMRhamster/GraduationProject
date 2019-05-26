package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;

import java.util.List;

public interface ITimeKeepBiz {
    void getTimeKeepById(int studentId,boolean onlyTime, OnTimeKeepListener onTimeKeepListener);
    void uploadTimeKeep(int studentId,int time, int typeId, OnTimeKeepListener onTimeKeepListener);

//    void getOnlyTotalTime(int studentId,boolean onlyTime);

    public interface OnTimeKeepListener{
        void timeKeepSuccess(List<InfoGroupBean> groupBeanList);
        void timeKeepFail(int code);
    }
}
