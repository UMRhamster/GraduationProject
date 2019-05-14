package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoService {
    @GET("video")
    Call<HttpData<List<Video>>> getVideoLimit10(@Query("start") int start);

    @GET("video")
    Call<HttpData<List<Video>>> getVideoByType(@Query("type")int start);
}
