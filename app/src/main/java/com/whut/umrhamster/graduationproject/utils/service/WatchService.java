package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WatchService {

    @GET("watch")
    Call<HttpData<List<Watch>>> getWatchByStudentId(@Query("studentId")int studentId);

    @GET("watch")
    Call<HttpData> isWatchExist(@Query("exist")boolean exist, @Query("studentId")int studentId, @Query("teacherId")int teacherId);

    @POST("watch")
    Call<HttpData> addWatch(@Query("studentId")int studentId,@Query("teacherId")int teacherId);

    @DELETE("watch")
    Call<HttpData> deleteWatchById(@Query("id")int watchId);

    @DELETE("watch")
    Call<HttpData> deleteWatchBySaT(@Query("studentId")int studentId,@Query("teacherId")int teacherId);

    @GET("watch")
    Call<HttpData<List<Watch>>> getNumOfWatchStudent(@Query("num_watch")int idf, @Query("teacherId")int teacherId);
    @GET("watch")
    Call<HttpData<List<Watch>>> getNumOfWatchTeacher(@Query("num_watch")int idf, @Query("studentId")int studentId);
}
