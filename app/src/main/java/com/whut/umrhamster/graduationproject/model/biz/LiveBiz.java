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

    @Override
    public void getLiveLimit10(int start, final OnLiveListener onLiveListener) {
        LiveService service = RetrofitUtil.retrofit.create(LiveService.class);
        Call<HttpData<List<Live>>> call = service.getLiveLimit10(start);
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
    public void updateLiveViewers(int liveId, int operation, final OnLiveListener onLiveListener) {
        LiveService service = RetrofitUtil.retrofit.create(LiveService.class);
        Call<HttpData> call = service.updateLiveViewers(liveId,operation);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
//                onLiveListener.onLiveSuccess();
                //向服务器提交数据，不用在客户端进行更新，就不对返回结果进行处理了
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {

            }
        });
    }
}
