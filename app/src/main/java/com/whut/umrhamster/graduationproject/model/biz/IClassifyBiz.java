package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Classify;

import java.util.List;

public interface IClassifyBiz {
    void getAllClassify(OnClassifyListener onClassifyListener);

    interface OnClassifyListener{
        void onSuccess(List<Classify> classifyList);
        void onFail(int code);
    }
}
