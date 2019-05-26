package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public interface IVideoBiz {
    void getVideoLimit10(int start, OnVideoListener onVideoListener);

    void getVideoByType(int type, OnVideoListener onVideoListener);

    void getVideoByTeacher(int teacherId,OnVideoListener onVideoListener);

    void getVideoByKeyword(String keyword, OnVideoListener onVideoListener);

    void addNumOfPlay(int videoId, OnVideoListener onVideoListener);

    public interface OnVideoListener{
        void onSuccess(List<Video> videoList);
        void onFail(int code);
    }
}
