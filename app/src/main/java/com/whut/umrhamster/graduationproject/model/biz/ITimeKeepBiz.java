package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;

import java.util.List;

public interface ITimeKeepBiz {
    void getTimeKeepById(int studentId,OnTimeKeepListener onTimeKeepListener);

    public interface OnTimeKeepListener{
        void timeKeepSuccess(List<InfoGroupBean> groupBeanList);
        void timeKeepFail(int code);
    }
}
