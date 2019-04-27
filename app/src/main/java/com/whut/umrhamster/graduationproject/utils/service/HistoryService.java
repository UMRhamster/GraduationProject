package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryService {
    @GET("history")
    Call<HttpData<List<History>>> getHistory(@Query("start")int start, @Query("studentId")int studentId, @Query("type")int type);
}
