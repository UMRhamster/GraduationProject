package com.whut.umrhamster.graduationproject.presenter;

import java.util.HashSet;

public interface IHistoryPresenter {
    void doHistory(int start, int studentId, int type);

    void existHistory(int studentId, int type, int contentId);
    void insertHistory(int studentId, int type, int contentId, int watchedTime);
    void updateHistory(int studentId, int type, int contentId, int watchedTime);
    void deleteHistory(int studentId, int type, HashSet<Integer> ids);
}
