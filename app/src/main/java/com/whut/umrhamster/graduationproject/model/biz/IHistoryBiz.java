package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.HashSet;
import java.util.List;

public interface IHistoryBiz {
    void getAllHistory(int start, int studentId, int type, OnHistoryListener onHistoryListener);

    void isHistoryExist(int studentId, int type, int contentId, OnHistoryExistListener onHistoryExistListener);

    void insertHistory(int studentId, int type, int contentId, int watchedTime, OnHistoryInsertListener onHistoryInsertListener);

    void deleteHistoryByIds(int student, int type, HashSet<Integer> ids, OnHistoryDeleteListener onHistoryDeleteListener);

    void updateHistory(int student, int type, int contentId, int watchedTime, OnHistoryUpdateListener onHistoryUpdateListener);

    public interface OnHistoryUpdateListener{
        void updateResult(int code);
    }

    public interface OnHistoryDeleteListener{
        void deleteResult(int code);
    }

    public interface OnHistoryInsertListener{
        void insertResult(int code);
    }
    public interface OnHistoryExistListener{
        void existResult(int code);
    }

    public interface OnHistoryListener{
        void onHistorySuccess(List<History> historyList);
        void onHistoryFail(int code);
    }
}
