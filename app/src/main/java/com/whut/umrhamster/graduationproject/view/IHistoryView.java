package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.History;

import java.util.List;

public interface IHistoryView {
    void onHistorySuccess(List<History> historyList);
    void onHistoryFail(int code);
}
