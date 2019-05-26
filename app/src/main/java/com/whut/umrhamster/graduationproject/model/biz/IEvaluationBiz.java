package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Evaluation;

import java.util.List;

public interface IEvaluationBiz {
    void getEvaluationsByTeacherId(int teacherId, OnEvaluationListener onEvaluationListener);

    public interface OnEvaluationListener{
        void onSuccess(List<Evaluation> evaluationList);
        void onFail(int code);
    }
}
