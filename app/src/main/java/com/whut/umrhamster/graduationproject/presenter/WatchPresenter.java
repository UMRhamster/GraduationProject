package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.model.biz.IWatchBiz;
import com.whut.umrhamster.graduationproject.model.biz.WatchBiz;
import com.whut.umrhamster.graduationproject.view.IWatchView;

import java.util.List;

public class WatchPresenter implements IWatchPresenter {
    private IWatchView watchView;
    private IWatchBiz watchBiz;
    private Handler handler;

    public WatchPresenter(IWatchView watchView){
        this.watchView = watchView;
        handler = new Handler(Looper.getMainLooper());
        watchBiz = new WatchBiz();

    }
    @Override
    public void doGetWatchByStudentId(int studentId) {
        watchBiz.getWatchByStudentId(studentId, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(final List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchSuccess(watchList);
                    }
                });
            }

            @Override
            public void onFail(int code) {
                watchView.onWatchFail(code);
            }
        });
    }

    @Override
    public void onDestroy() {
        watchView = null;
    }
}
