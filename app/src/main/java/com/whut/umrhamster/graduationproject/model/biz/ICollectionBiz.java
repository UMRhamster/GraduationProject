package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public interface ICollectionBiz {
    void getCollectionById(int studentId, OnCollectionListener onCollectionListener);

    public interface OnCollectionListener{
        void onSuccess(List<Collection> videoList);
        void onFail(int code);
    }
}
