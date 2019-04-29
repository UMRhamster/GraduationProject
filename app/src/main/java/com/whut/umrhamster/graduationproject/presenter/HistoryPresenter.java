package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.biz.HistoryBiz;
import com.whut.umrhamster.graduationproject.model.biz.IHistoryBiz;
import com.whut.umrhamster.graduationproject.view.IHistoryView;

import java.util.HashSet;
import java.util.List;


public class HistoryPresenter implements IHistoryPresenter {
    private IHistoryBiz historyBiz;
    private Handler handler;
    private IHistoryView historyView;

    public HistoryPresenter(IHistoryView historyView){
        this.historyView = historyView;
        handler = new Handler(Looper.getMainLooper());
        historyBiz = new HistoryBiz();
    }
    @Override
    public void doHistory(int start, int studentId, int type) {
        historyBiz.getAllHistory(start, studentId, type, new IHistoryBiz.OnHistoryListener() {
            @Override
            public void onHistorySuccess(final List<History> historyList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.onHistorySuccess(historyList);
                    }
                });
            }

            @Override
            public void onHistoryFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.onHistoryFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void existHistory(int studentId, int type, int contentId) {
        historyBiz.isHistoryExist(studentId, type, contentId, new IHistoryBiz.OnHistoryExistListener() {
            @Override
            public void existResult(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.existResult(code);
                    }
                });
            }
        });
    }

    @Override
    public void insertHistory(int studentId, int type, int contentId, int watchedTime) {
        historyBiz.insertHistory(studentId, type, contentId, watchedTime, new IHistoryBiz.OnHistoryInsertListener() {
            @Override
            public void insertResult(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.insertResult(code);
                    }
                });
            }
        });
    }

    @Override
    public void updateHistory(int studentId, int type, int contentId, int watchedTime) {
        historyBiz.updateHistory(studentId, type, contentId, watchedTime, new IHistoryBiz.OnHistoryUpdateListener() {
            @Override
            public void updateResult(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.updateResult(code);
                    }
                });
            }
        });
    }

    @Override
    public void deleteHistory(int studentId, int type, HashSet<Integer> ids) {
        historyBiz.deleteHistoryByIds(studentId, type, ids, new IHistoryBiz.OnHistoryDeleteListener() {
            @Override
            public void deleteResult(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        historyView.deleteResult(code);
                    }
                });
            }
        });
    }
}
