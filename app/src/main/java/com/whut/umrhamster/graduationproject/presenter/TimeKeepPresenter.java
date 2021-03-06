package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;
import com.whut.umrhamster.graduationproject.model.biz.ITimeKeepBiz;
import com.whut.umrhamster.graduationproject.model.biz.TimeKeepBiz;
import com.whut.umrhamster.graduationproject.view.ITimeKeepView;

import java.util.List;

public class TimeKeepPresenter implements ITimeKeepPresenter {
    private ITimeKeepView timeKeepView;
    private ITimeKeepBiz timeKeepBiz;
    private Handler handler;

    public TimeKeepPresenter(ITimeKeepView timeKeepView){
        this.timeKeepView = timeKeepView;
        timeKeepBiz = new TimeKeepBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetTimeKeepById(int studentId, boolean onlyTime) {
        timeKeepBiz.getTimeKeepById(studentId,onlyTime, new ITimeKeepBiz.OnTimeKeepListener() {
            @Override
            public void timeKeepSuccess(final List<InfoGroupBean> groupBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeKeepView.onTimeKeepSuccess(groupBeanList);
                    }
                });
            }

            @Override
            public void timeKeepFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeKeepView.onTimeKeepFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doUploadTimeKeep(int studentId, int typeId, int time) {
        timeKeepBiz.uploadTimeKeep(studentId, time, typeId, new ITimeKeepBiz.OnTimeKeepListener() {
            @Override
            public void timeKeepSuccess(List<InfoGroupBean> groupBeanList) {

            }

            @Override
            public void timeKeepFail(int code) {
                timeKeepView.onTimeKeepFail(code);
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
