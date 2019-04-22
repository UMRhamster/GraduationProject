package com.whut.umrhamster.graduationproject.view;

import android.view.View;

public interface IInitWidgetView {
    //Activity控件绑定
    void initView();
    //事件绑定
    void initEvent();
    //Fragment控件绑定
    void initView(View view);
}
