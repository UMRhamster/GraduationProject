package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public interface ICollectionBiz {
    void getCollectionById(int studentId, OnCollectionListener onCollectionListener);
    void getCollectionLimit10(int start,int studentId,OnCollectionListener onCollectionListener);
    void isCollectionExist(int studentId,int videoId,OnCollectionListener onCollectionListener);

    void insertCollection(int studentId,int videoId,OnCollectionListener onCollectionListener);
    void deleteCollectionById(int id,OnCollectionListener onCollectionListener);
    void deleteCollectionBySaV(int studentId,int videoId,OnCollectionListener onCollectionListener);

    public interface OnCollectionListener{
        void onSuccess(List<Collection> videoList);
        void onFail(int code);
    }
}
