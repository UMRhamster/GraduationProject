package com.whut.umrhamster.graduationproject.utils.service;


import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiveService {
    @GET("live")
    Call<HttpData<List<Live>>> getAllLive(@Query("all") boolean all);
}