package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Watch;

import java.util.List;

public interface IWatchView {
    void onWatchSuccess(List<Watch> watchList);
    void onWatchFail(int code);

    void onWatchExist(boolean exist);
    void onAddWatchSuccess();
    void onDeleteWatchSuccess();

    void onGetNumStudents(List<Watch> watchList);
    void onGetNumTeachers(List<Watch> watchList);
}
