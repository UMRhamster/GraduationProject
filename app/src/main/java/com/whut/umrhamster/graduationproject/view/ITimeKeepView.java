package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.InfoGroupBean;

import java.util.List;

public interface ITimeKeepView {
    void onTimeKeepSuccess(List<InfoGroupBean> groupBeanList);
    void onTimeKeepFail(int code);
}
