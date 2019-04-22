package com.whut.umrhamster.graduationproject.model.biz;

import android.util.Log;

import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ThreadPoolUtils;
import com.whut.umrhamster.graduationproject.utils.http.NetworkUtil;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;

public class UserBiz implements IUserBiz {
    @Override
    public void login(final String userName, final String userPassword, final OnLoginListener onLoginListener) {
        ThreadPoolUtils.runTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400 );  //延迟400毫秒，等待cpbbutton动画效果结束
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RetrofitUtil.login(userName,userPassword,onLoginListener);
            }
        });
    }

    @Override
    public void loginWithoutPassword(final String email, final OnLoginListener onLoginListener) {
        RetrofitUtil.loginWithoutPassword(email,onLoginListener);
    }

    @Override
    public void signup(final Student student, final String password, final OnSignupListener onSignupListener) {
        ThreadPoolUtils.runTaskInThreadPool(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RetrofitUtil.signup(student,password,onSignupListener);
                //http请求服务器进行注册，根据返回结果进行不同调用
                //result
//                if (result){
//                    onSignupListener.signupSuccess();
//                }else {
//
//                }
            }
        });
    }


}
