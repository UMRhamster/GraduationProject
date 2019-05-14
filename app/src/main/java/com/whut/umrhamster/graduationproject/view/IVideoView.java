package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Video;

import java.util.List;

public interface IVideoView {
    void onVideoSuccess(List<Video> videoList);
    void onVideoFail(int code);

    void onVideoTypeSuccess(List<Video> videoList);
}
