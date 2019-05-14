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
}
