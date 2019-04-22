package com.whut.umrhamster.graduationproject.view;

import android.annotation.TargetApi;

import com.whut.umrhamster.graduationproject.model.bean.Live;

import java.util.List;

public interface ILiveView {
    void onAllLiveSuccess(List<Live> liveList);
    void onAllLiveFail(int code);
}
