package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;

public class LiveBiz implements ILiveBiz {
    @Override
    public void getAllLive(OnLiveListener onLiveListener) {
        RetrofitUtil.getAllLive(true,onLiveListener);
    }
}
