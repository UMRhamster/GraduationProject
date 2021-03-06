package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.TimeKeepService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeKeepBiz implements ITimeKeepBiz {
    @Override
    public void getTimeKeepById(int studentId, boolean onlyTime, final OnTimeKeepListener onTimeKeepListener) {
        TimeKeepService service = RetrofitUtil.retrofit.create(TimeKeepService.class);
        Call<HttpData<List<InfoGroupBean>>> call = service.getTimeKeepById(studentId, onlyTime);
        call.enqueue(new Callback<HttpData<List<InfoGroupBean>>>() {
            @Override
            public void onResponse(Call<HttpData<List<InfoGroupBean>>> call, Response<HttpData<List<InfoGroupBean>>> response) {
                HttpData<List<InfoGroupBean>> data = response.body();
                if (data != null){
                    onTimeKeepListener.timeKeepSuccess(data.getData());
                }else {
                    onTimeKeepListener.timeKeepFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<InfoGroupBean>>> call, Throwable t) {
                onTimeKeepListener.timeKeepFail(-1);
            }
        });
    }

    @Override
    public void uploadTimeKeep(int studentId, int time, int typeId, final OnTimeKeepListener onTimeKeepListener) {
        TimeKeepService service = RetrofitUtil.retrofit.create(TimeKeepService.class);
        Call<HttpData> call = service.uploadTimeKeep(studentId,typeId,time);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {

            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onTimeKeepListener.timeKeepFail(-1);
            }
        });
    }
}
