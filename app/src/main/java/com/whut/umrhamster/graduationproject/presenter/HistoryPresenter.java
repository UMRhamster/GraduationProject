package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.biz.HistoryBiz;
import com.whut.umrhamster.graduationproject.model.biz.IHistoryBiz;
import com.whut.umrhamster.graduationproject.view.IHistoryView;

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
}
