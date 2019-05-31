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
    public void doGetWatchLimit20(int studentId, int start) {
        watchBiz.getWatchLimit20(studentId, start, new IWatchBiz.OnWatchListener() {
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
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void isWatchExist(int studentId, int teacherId) {
        watchBiz.isWatchExist(studentId, teacherId, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchExist(true);
                    }
                });
            }

            @Override
            public void onFail(int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchExist(false);
                    }
                });
            }
        });
    }

    @Override
    public void doAddWatch(int studentId, int teacherId) {
        watchBiz.addWatch(studentId, teacherId, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(final List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onAddWatchSuccess();
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doDeleteWatchById(int watchId) {
        watchBiz.deleteWatchById(watchId, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onDeleteWatchSuccess();
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchFail(code);
                    }
                });
            }
        });
    }


    @Override
    public void doDeleteWatchBySaT(int studentId, int teacherId) {
        watchBiz.deleteWatchBySaT(studentId, teacherId, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onDeleteWatchSuccess();
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doGetNumOfWatch(final int idf, int id) {  //1-获取教师数 2-获取学生数
        watchBiz.getNumOfWatch(idf, id, new IWatchBiz.OnWatchListener() {
            @Override
            public void onSuccess(final List<Watch> watchList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (idf == 1){
                            watchView.onGetNumTeachers(watchList);
                        }else if (idf == 2){
                            watchView.onGetNumStudents(watchList);
                        }
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        watchView.onWatchFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        watchView = null;
    }
}
