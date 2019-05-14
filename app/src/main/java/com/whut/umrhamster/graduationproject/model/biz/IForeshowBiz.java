package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;

import java.util.List;

public interface IForeshowBiz {
    void getForeshowLimit10(int start, OnForeshowListener onForeshowListener);

    public interface OnForeshowListener{
        void onForeshowSuccess(List<Foreshow> foreshowList);
        void onForeshowFail(int code);
    }
}
