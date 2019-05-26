package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Evaluation;

import java.util.List;

public interface IEvaluationView {
    void onEvaluationByTeacherId(List<Evaluation> evaluationList);
    void onEvaluationFail(int code);
}
