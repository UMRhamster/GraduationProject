package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Evaluation;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.EvaluationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvaluationBiz implements IEvaluationBiz {
    @Override
    public void getEvaluationsByTeacherId(int teacherId, final OnEvaluationListener onEvaluationListener) {
        EvaluationService service = RetrofitUtil.retrofit.create(EvaluationService.class);
        Call<HttpData<List<Evaluation>>> call = service.getEvaluationsByTeacherId(teacherId);
        call.enqueue(new Callback<HttpData<List<Evaluation>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Evaluation>>> call, Response<HttpData<List<Evaluation>>> response) {
                HttpData<List<Evaluation>> data = response.body();
                if (data != null){
                    onEvaluationListener.onSuccess(data.getData());
                }else {
                    onEvaluationListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Evaluation>>> call, Throwable t) {
                onEvaluationListener.onFail(-1);
            }
        });
    }
}
