package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.List;

public interface IHistoryBiz {
    void getAllHistory(int start, int studentId, int type, OnHistoryListener onHistoryListener);
    void getVideoHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener);
    void getLiveHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener);

    public interface OnHistoryListener{
        void onHistorySuccess(List<History> historyList);
        void onHistoryFail(int code);
    }
}
