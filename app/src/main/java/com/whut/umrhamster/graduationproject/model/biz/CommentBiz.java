package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Comment;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.CommentService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentBiz implements ICommentBiz {
    @Override
    public void getCommentsByVideoId(int videoId, final OnCommentListener onCommentListener) {
        CommentService service = RetrofitUtil.retrofit.create(CommentService.class);
        Call<HttpData<List<Comment>>> call = service.getCommentsByVideoId(videoId);
        call.enqueue(new Callback<HttpData<List<Comment>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Comment>>> call, Response<HttpData<List<Comment>>> response) {
                HttpData<List<Comment>> data = response.body();
                if (data != null){
                    onCommentListener.onSuccess(data.getData());
                }else {
                    onCommentListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Comment>>> call, Throwable t) {
                onCommentListener.onFail(-1);
            }
        });

    }

    @Override
    public void getCommentLimit10(int videoId, int start, final OnCommentListener onCommentListener) {
        CommentService service = RetrofitUtil.retrofit.create(CommentService.class);
        Call<HttpData<List<Comment>>> call = service.getCommentsLimit10(videoId,start);
        call.enqueue(new Callback<HttpData<List<Comment>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Comment>>> call, Response<HttpData<List<Comment>>> response) {
                HttpData<List<Comment>> data = response.body();
                if (data != null){
                    onCommentListener.onSuccess(data.getData());
                }else {
                    onCommentListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Comment>>> call, Throwable t) {
                onCommentListener.onFail(-1);
            }
        });
    }

    @Override
    public void insertComment(int videoId, int studentId, String content, final OnCommentListener onCommentListener) {
        CommentService service = RetrofitUtil.retrofit.create(CommentService.class);
        Call<HttpData> call = service.insertComment(videoId, studentId, content);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null && data.getCode() == 2000){
                    onCommentListener.onFail(2000);  //懒得写新的回调
                }else {
                    onCommentListener.onFail(-2);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onCommentListener.onFail(-1);
            }
        });
    }
}
