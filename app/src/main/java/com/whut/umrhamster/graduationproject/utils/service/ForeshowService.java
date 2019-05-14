package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForeshowService {
    @GET("foreshow")
    Call<HttpData<List<Foreshow>>> getForeshowLimit10(@Query("start")int start);
}
