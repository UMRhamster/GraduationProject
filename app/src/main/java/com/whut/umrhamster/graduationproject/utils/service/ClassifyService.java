package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClassifyService {
    @GET("classify")
    Call<HttpData<List<Classify>>> getClassify();
}
