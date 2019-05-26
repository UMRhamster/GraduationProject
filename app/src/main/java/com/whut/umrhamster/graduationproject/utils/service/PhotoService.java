package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PhotoService {
    @Multipart
    @POST("photo")
    Call<HttpData> uploadPhoto(@Query ("id")int studentId,@Part MultipartBody.Part file);
}
