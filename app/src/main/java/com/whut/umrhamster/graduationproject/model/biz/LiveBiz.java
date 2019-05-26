package com.whut.umrhamster.graduationproject.model.biz;


import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.LiveService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveBiz implements ILiveBiz {
    @Override
    public void getAllLive(OnLiveListener onLiveListener) {
        RetrofitUtil.getAllLive(true,onLiveListener);
    }

    @Override
    public void getTypeLive(int type, final OnLiveListener onLiveListener) {
        LiveService service = RetrofitUtil.retrofit.create(LiveService.class);
        Call<HttpData<List<Live>>> call = service.getLiveByType(type);
        call.enqueue(new Callback<HttpData<List<Live>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Live>>> call, Response<HttpData<List<Live>>> response) {
                HttpData<List<Live>> data = response.body();
                if (data != null){
                    onLiveListener.onLiveSuccess(data.getData());
                }else {
                    onLiveListener.onLiveFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Live>>> call, Throwable t) {
                onLiveListener.onLiveFail(-1);
            }
        });
    }

    @Override
    public void getLiveByKeyword(String keyword, final OnLiveListener onLiveListener) {
        LiveService service = RetrofitUtil.retrofit.create(LiveService.class);
        Call<HttpData<List<Live>>> call = service.getLiveByKeyword(keyword);
        call.enqueue(new Callback<HttpData<List<Live>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Live>>> call, Response<HttpData<List<Live>>> response) {
                HttpData<List<Live>> data = response.body();
                if (data != null){
                    onLiveListener.onLiveSuccess(data.getData());
                }else {
                    onLiveListener.onLiveFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Live>>> call, Throwable t) {
                onLiveListener.onLiveFail(-1);
            }
        });
    }
}
