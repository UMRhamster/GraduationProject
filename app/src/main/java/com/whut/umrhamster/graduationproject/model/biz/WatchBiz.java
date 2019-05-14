package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.WatchService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchBiz implements IWatchBiz {
    @Override
    public void getWatchByStudentId(int studentId, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);;
        Call<HttpData<List<Watch>>> call = service.getWatchByStudentId(studentId);
        call.enqueue(new Callback<HttpData<List<Watch>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Watch>>> call, Response<HttpData<List<Watch>>> response) {
                HttpData<List<Watch>> data = response.body();
                if (data != null){
                    onWatchListener.onSuccess(data.getData());
                }else {
                    onWatchListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Watch>>> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }
}
