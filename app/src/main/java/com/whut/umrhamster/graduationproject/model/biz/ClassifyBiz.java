package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.ClassifyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassifyBiz implements IClassifyBiz {
    @Override
    public void getAllClassify(final OnClassifyListener onClassifyListener) {
        ClassifyService service = RetrofitUtil.retrofit.create(ClassifyService.class);
        Call<HttpData<List<Classify>>> call = service.getClassify();
        call.enqueue(new Callback<HttpData<List<Classify>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Classify>>> call, Response<HttpData<List<Classify>>> response) {
                HttpData<List<Classify>> data = response.body();
                if (data != null){
                    onClassifyListener.onSuccess(data.getData());
                }else {
                    onClassifyListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Classify>>> call, Throwable t) {
                onClassifyListener.onFail(-1);
            }
        });
    }
}
