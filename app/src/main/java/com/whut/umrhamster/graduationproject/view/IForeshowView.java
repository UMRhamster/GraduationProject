package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Foreshow;

import java.util.List;

public interface IForeshowView {
    void onGetForeshowSuccess(List<Foreshow> foreshowList);
    void onGetForeshowFail(int code);
}
