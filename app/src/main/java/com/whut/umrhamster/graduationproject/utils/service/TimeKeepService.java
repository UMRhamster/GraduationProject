package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TimeKeepService {
    @GET("timekeep")
    Call<HttpData<List<InfoGroupBean>>> getTimeKeepById(@Query("studentId")int studentId);
}
