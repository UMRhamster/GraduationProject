package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;
import com.whut.umrhamster.graduationproject.model.biz.ForeshowBiz;
import com.whut.umrhamster.graduationproject.model.biz.IForeshowBiz;
import com.whut.umrhamster.graduationproject.view.IForeshowView;

import java.util.List;

public class ForeshowPresenter implements IForeshowPresenter {
    private IForeshowView foreshowView;
    private IForeshowBiz foreshowBiz;
    private Handler handler;

    public ForeshowPresenter(IForeshowView foreshowView){
        this.foreshowView = foreshowView;
        foreshowBiz = new ForeshowBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetForeshowLimit10(int start) {
        foreshowBiz.getForeshowLimit10(start, new IForeshowBiz.OnForeshowListener() {
            @Override
            public void onForeshowSuccess(final List<Foreshow> foreshowList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        foreshowView.onGetForeshowSuccess(foreshowList);
                    }
                });
            }

            @Override
            public void onForeshowFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        foreshowView.onGetForeshowFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        foreshowView = null;
    }
}
