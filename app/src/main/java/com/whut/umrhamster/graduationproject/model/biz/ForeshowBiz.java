package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.ForeshowService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForeshowBiz implements IForeshowBiz {
    @Override
    public void getForeshowLimit10(int start, final OnForeshowListener onForeshowListener) {
        ForeshowService service = RetrofitUtil.retrofit.create(ForeshowService.class);
        Call<HttpData<List<Foreshow>>> call = service.getForeshowLimit10(start);
        call.enqueue(new Callback<HttpData<List<Foreshow>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Foreshow>>> call, Response<HttpData<List<Foreshow>>> response) {
                HttpData<List<Foreshow>> data = response.body();
                if (data != null){
                    onForeshowListener.onForeshowSuccess(data.getData());
                }else {
                    onForeshowListener.onForeshowFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Foreshow>>> call, Throwable t) {
                onForeshowListener.onForeshowFail(-1);
            }
        });
    }
}
