package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.model.biz.UserBiz;
import com.whut.umrhamster.graduationproject.view.ILoginView;
import com.whut.umrhamster.graduationproject.view.IVerifyView;

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    Handler handler;
    IUserBiz iUserBiz;

    public LoginPresenterCompl(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        handler = new Handler(Looper.getMainLooper());
        initUser();
    }
    @Override
    public void doLogin(String userName, String userPassword) {
        iUserBiz.login(userName, userPassword, new IUserBiz.OnLoginListener() {

            @Override
            public void onLoginSuccess(final Student student) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.onSuccess(student);
                    }
                });
            }

            @Override
            public void onLoginFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.onFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void doLoginWithVerification(String email) {
        iUserBiz.loginWithoutPassword(email, new IUserBiz.OnLoginListener() {
            @Override
            public void onLoginSuccess(final Student student) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.onSuccess(student);
                    }
                });
            }

            @Override
            public void onLoginFail(int code) {

            }
        });
//        iUserBiz.loginWithoutPassword(email);
    }

    private void initUser(){
        iUserBiz = new UserBiz();
    }
}
