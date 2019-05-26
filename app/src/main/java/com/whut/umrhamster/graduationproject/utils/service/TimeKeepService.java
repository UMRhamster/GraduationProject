package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TimeKeepService {
    @GET("timekeep")
    Call<HttpData<List<InfoGroupBean>>> getTimeKeepById(@Query("studentId")int studentId,@Query("only_time")boolean onlyTime);

//    @GET("timekeep")
//    Call<HttpData<List<InfoGroupBean>>> getTotalTime(@Query("studentId") int studentId,@Query("only_time")boolean onlyTime);

    @POST("timekeep")
    Call<HttpData> uploadTimeKeep(@Query("studentId")int studentId,@Query("typeId")int typeId,@Query("time")int time);  //InfoGroupBean 在这里只用来取学生学习的总时长，无其他作用

//    Call<HttpData<List<InfoGroupBean>>> updateTimeKeep(@Q)
}
