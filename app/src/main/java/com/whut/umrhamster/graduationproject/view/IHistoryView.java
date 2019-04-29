package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.List;

public interface IHistoryView {
    void onHistorySuccess(List<History> historyList);
    void onHistoryFail(int code);

    void existResult(int code);
    void insertResult(int code);
    void updateResult(int code);
    void deleteResult(int code);
}
