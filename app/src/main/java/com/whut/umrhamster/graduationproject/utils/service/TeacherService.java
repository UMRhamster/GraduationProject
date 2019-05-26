package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeacherService {
    @GET("teacher")
    Call<HttpData<List<Teacher>>> getTeacheByKeyword(@Query("keyword")String keyword);
}
