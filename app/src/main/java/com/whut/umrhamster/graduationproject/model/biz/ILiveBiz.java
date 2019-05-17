package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Live;

import java.util.List;

public interface ILiveBiz {
    void getAllLive(OnLiveListener onLiveListener);

    void getTypeLive(int type,OnLiveListener onLiveListener);

    interface OnLiveListener{
        void onLiveSuccess(List<Live> liveList);
        void onLiveFail(int code);
    }
}
