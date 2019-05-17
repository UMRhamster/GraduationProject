package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.VideoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoBiz implements IVideoBiz {
    @Override
    public void getVideoLimit10(int start, final OnVideoListener onVideoListener) {
        VideoService service = RetrofitUtil.retrofit.create(VideoService.class);
        Call<HttpData<List<Video>>> call = service.getVideoLimit10(start);
        call.enqueue(new Callback<HttpData<List<Video>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Video>>> call, Response<HttpData<List<Video>>> response) {
                HttpData<List<Video>> data = response.body();
                if (data != null){
                    onVideoListener.onSuccess(data.getData());
                }else {
                    onVideoListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Video>>> call, Throwable t) {
                onVideoListener.onFail(-1);
            }
        });
    }

    //分类查询
    @Override
    public void getVideoByType(int type, final OnVideoListener onVideoListener) {
        VideoService service = RetrofitUtil.retrofit.create(VideoService.class);
        Call<HttpData<List<Video>>> call = service.getVideoByType(type);
        call.enqueue(new Callback<HttpData<List<Video>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Video>>> call, Response<HttpData<List<Video>>> response) {
                HttpData<List<Video>> data = response.body();
                if (data!=null){
                    onVideoListener.onSuccess(data.getData());
                }else {
                    onVideoListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Video>>> call, Throwable t) {
                onVideoListener.onFail(-1);
            }
        });
    }

    @Override
    public void getVideoByTeacher(int teacherId, final OnVideoListener onVideoListener) {
        VideoService service = RetrofitUtil.retrofit.create(VideoService.class);
        Call<HttpData<List<Video>>> call = service.getVideoByTeacher(teacherId);
        call.enqueue(new Callback<HttpData<List<Video>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Video>>> call, Response<HttpData<List<Video>>> response) {
                HttpData<List<Video>> data = response.body();
                if (data != null){
                    onVideoListener.onSuccess(data.getData());
                }else {
                    onVideoListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Video>>> call, Throwable t) {
                onVideoListener.onFail(-1);
            }
        });
    }
}
