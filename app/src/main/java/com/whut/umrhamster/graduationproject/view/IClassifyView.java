package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Classify;

import java.util.List;

public interface IClassifyView {
    void onClassifySuccess(List<Classify> classifyList);
    void onClassifyFail(int code);
}
