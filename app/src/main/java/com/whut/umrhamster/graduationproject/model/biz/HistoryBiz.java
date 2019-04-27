package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;

public class HistoryBiz implements IHistoryBiz {
    @Override
    public void getAllHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener) {
        RetrofitUtil.getHistory(start,studentId,type,onHistoryListener);
    }

    @Override
    public void getVideoHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener) {
        RetrofitUtil.getHistory(start,studentId,type,onHistoryListener);
    }

    @Override
    public void getLiveHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener) {
        RetrofitUtil.getHistory(start,studentId,type,onHistoryListener);
    }
}
