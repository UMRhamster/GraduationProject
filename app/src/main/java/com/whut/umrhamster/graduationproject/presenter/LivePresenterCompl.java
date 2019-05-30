package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.biz.ILiveBiz;
import com.whut.umrhamster.graduationproject.model.biz.LiveBiz;
import com.whut.umrhamster.graduationproject.view.ILiveView;

import java.util.List;


public class LivePresenterCompl implements ILivePresenter {
    ILiveBiz liveBiz;
    ILiveView liveView;
    Handler handler;

    public LivePresenterCompl(ILiveView liveView){
        this.liveView = liveView;
        liveBiz = new LiveBiz();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getAllLive() {
        liveBiz.getAllLive(new ILiveBiz.OnLiveListener() {
            @Override
            public void onLiveSuccess(final List<Live> liveList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveSuccess(liveList);
                    }
                });
            }

            @Override
            public void onLiveFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetTypeLive(int type) {
        liveBiz.getTypeLive(type, new ILiveBiz.OnLiveListener() {
            @Override
            public void onLiveSuccess(final List<Live> liveList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onTypeLiveSuccess(liveList);
                    }
                });
            }

            @Override
            public void onLiveFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetLiveByKeyword(String keyword) {
        liveBiz.getLiveByKeyword(keyword, new ILiveBiz.OnLiveListener() {
            @Override
            public void onLiveSuccess(final List<Live> liveList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveSuccess(liveList); //借用，不再添加方法了，两个接口不会有同时被调用的场景
                    }
                });
            }

            @Override
            public void onLiveFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetLiveLimit10(int start) {
        liveBiz.getLiveLimit10(start, new ILiveBiz.OnLiveListener() {
            @Override
            public void onLiveSuccess(final List<Live> liveList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveSuccess(liveList);
                    }
                });
            }

            @Override
            public void onLiveFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveView.onAllLiveFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doUpdateLiveViewers(int liveId, int operation) {
        liveBiz.updateLiveViewers(liveId, operation, new ILiveBiz.OnLiveListener() {
            @Override
            public void onLiveSuccess(List<Live> liveList) {
                //不做处理，因为不在客户端进行更新
            }

            @Override
            public void onLiveFail(int code) {

            }
        });
    }

    @Override
    public void onDestroy() {
        liveView = null;
    }
}
