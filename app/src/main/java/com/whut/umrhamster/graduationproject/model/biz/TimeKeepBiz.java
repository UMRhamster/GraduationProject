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
    public void getTimeKeepById(int studentId, final OnTimeKeepListener onTimeKeepListener) {
        TimeKeepService service = RetrofitUtil.retrofit.create(TimeKeepService.class);
        Call<HttpData<List<InfoGroupBean>>> call = service.getTimeKeepById(studentId);
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
}
