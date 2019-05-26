package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Evaluation;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EvaluationService {
    @GET("evaluation")
    Call<HttpData<List<Evaluation>>> getEvaluationsByTeacherId(@Query("teacherId")int teacherId);
}
