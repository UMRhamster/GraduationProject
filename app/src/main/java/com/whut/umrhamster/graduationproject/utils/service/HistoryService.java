package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface HistoryService {
    @GET("history")
    Call<HttpData<List<History>>> getHistory(@Query("start")int start, @Query("studentId")int studentId, @Query("type")int type);

    //判断记录是否存在
    @GET("history")
    Call<HttpData> isHistoryExist(@Query("studentId")int studentId, @Query("type")int type, @Query("contentId")int contentId, @Query("isExist")boolean isExist);

    //根据ids删除复数历史记录
    @DELETE("history")
    Call<HttpData> deleteHistoryByIds(@Query("studentId")int studentId, @Query("type")int type, @Query("ids")HashSet<Integer> ids);

//    @DELETE("")
//    Call<HttpData> deleteAll(@Query("studentId"))

//    @DELETE("history")
//    Call<HttpData> deleteHistory();

    //新增历史记录
    @POST("history")
    Call<HttpData> insertHistory(@Query("studentId")int studentId, @Query("type")int type, @Query("contentId")int contentId, @Query("watchedTime")int watchedTime);

    @PUT("history")
    Call<HttpData> updateHistory(@Query("studentId")int studentId, @Query("type")int type, @Query("contentId")int contentId, @Query("watchedTime")int watchedTime);

    //后台没有设置参数可选，该接口不需要studentId,后台不做处理
//    @DELETE("history")
//    Call<HttpData> deleteHistoryByIdsWithoutType(@Query("studentId")int studentId, @Query("type")int type, @Query("ids")HashSet<Integer> ids);
}
