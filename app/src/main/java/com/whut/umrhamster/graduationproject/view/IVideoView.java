package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

//吐槽不能使用接口的默认方法问题
public interface IVideoView {
    void onVideoSuccess(List<Video> videoList);
    void onVideoFail(int code);

    void onVideoTypeSuccess(List<Video> videoList);
    void onVideoTeacherSuccess(List<Video> videoList);
}
