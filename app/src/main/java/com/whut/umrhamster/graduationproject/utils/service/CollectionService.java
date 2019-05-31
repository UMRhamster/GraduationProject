package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CollectionService {
    @GET("collection")
    Call<HttpData<List<Collection>>> getCollectionById(@Query("studentId") int studentId);
    @GET("collection")
    Call<HttpData> isCollectionExist(@Query("isExist")boolean isExist,@Query("studentId")int studentId,@Query("videoId")int videoId);

    @POST("collection")
    Call<HttpData> insertCollection(@Query("studentId")int studentId,@Query("videoId")int videoId);

    @DELETE("collection")
    Call<HttpData> deleteCollectionById(@Query("id")int id);

    @DELETE("collection")
    Call<HttpData> deleteCollectionBySaV(@Query("studentId")int studentId,@Query("videoId")int videoId);
}
