package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentService {
    @GET("comment")
    Call<HttpData<List<Comment>>> getCommentsByVideoId(@Query("videoId")int videoId);

    @GET("comment")
    Call<HttpData<List<Comment>>> getCommentsLimit10(@Query("videoId")int videoId,@Query("start")int start);

    @POST("comment")
    Call<HttpData> insertComment(@Query("videoId")int videoId,@Query("studentId")int studentId,@Query("content")String content);
}
