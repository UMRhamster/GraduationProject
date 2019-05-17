package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.model.biz.IVideoBiz;
import com.whut.umrhamster.graduationproject.model.biz.VideoBiz;
import com.whut.umrhamster.graduationproject.view.IVideoView;

import java.util.List;

public class VideoPresenter implements IVideoPresenter {
    private IVideoView videoView;
    private IVideoBiz videoBiz;
    private Handler handler;
    public VideoPresenter(IVideoView videoView){
        this.videoView = videoView;
        videoBiz = new VideoBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetVideoLimit10(int start) {
        videoBiz.getVideoLimit10(start, new IVideoBiz.OnVideoListener() {
            @Override
            public void onSuccess(final List<Video> videoList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoSuccess(videoList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetVideoByType(int type) {
        videoBiz.getVideoByType(type, new IVideoBiz.OnVideoListener() {
            @Override
            public void onSuccess(final List<Video> videoList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoTypeSuccess(videoList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetVideoByTeacher(int teacherId) {
        videoBiz.getVideoByTeacher(teacherId, new IVideoBiz.OnVideoListener() {
            @Override
            public void onSuccess(final List<Video> videoList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoTeacherSuccess(videoList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoView.onVideoFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        videoView = null;
    }
}
