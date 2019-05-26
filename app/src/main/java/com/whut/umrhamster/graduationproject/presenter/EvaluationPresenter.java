package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Evaluation;
import com.whut.umrhamster.graduationproject.model.biz.EvaluationBiz;
import com.whut.umrhamster.graduationproject.model.biz.IEvaluationBiz;
import com.whut.umrhamster.graduationproject.view.IEvaluationView;

import java.util.List;

public class EvaluationPresenter implements IEvaluationPresenter {
    private IEvaluationBiz evaluationBiz;
    private IEvaluationView evaluationView;
    private Handler handler;

    public EvaluationPresenter(IEvaluationView evaluationView){
        this.evaluationView = evaluationView;
        evaluationBiz = new EvaluationBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetEvaluationByTeacherId(int teacherId) {
        evaluationBiz.getEvaluationsByTeacherId(teacherId, new IEvaluationBiz.OnEvaluationListener() {
            @Override
            public void onSuccess(final List<Evaluation> evaluationList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        evaluationView.onEvaluationByTeacherId(evaluationList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        evaluationView.onEvaluationFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        evaluationView = null;
    }
}
