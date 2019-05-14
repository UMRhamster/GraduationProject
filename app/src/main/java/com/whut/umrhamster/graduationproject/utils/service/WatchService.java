package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WatchService {

    @GET("watch")
    Call<HttpData<List<Watch>>> getWatchByStudentId(@Query("studentId")int studentId);
}
