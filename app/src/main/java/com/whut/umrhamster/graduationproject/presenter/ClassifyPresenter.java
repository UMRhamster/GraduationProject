package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Classify;
import com.whut.umrhamster.graduationproject.model.biz.ClassifyBiz;
import com.whut.umrhamster.graduationproject.model.biz.IClassifyBiz;
import com.whut.umrhamster.graduationproject.view.IClassifyView;

import java.util.List;

public class ClassifyPresenter implements IClassifyPresenter {
    private IClassifyBiz classifyBiz;
    private IClassifyView classifyView;
    private Handler handler;

    public ClassifyPresenter(IClassifyView classifyView){
        this.classifyView = classifyView;;
        classifyBiz = new ClassifyBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetAllClassify() {
        classifyBiz.getAllClassify(new IClassifyBiz.OnClassifyListener() {
            @Override
            public void onSuccess(final List<Classify> classifyList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        classifyView.onClassifySuccess(classifyList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       classifyView.onClassifyFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        classifyView = null;
    }
}
