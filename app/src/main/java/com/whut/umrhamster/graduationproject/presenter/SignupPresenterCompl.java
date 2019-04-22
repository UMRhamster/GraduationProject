package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.model.biz.UserBiz;
import com.whut.umrhamster.graduationproject.view.ISignupView;


public class SignupPresenterCompl implements ISignupPresenter {
    private ISignupView signupView;
    private IUserBiz userBiz;
    private Handler handler;

    public SignupPresenterCompl(ISignupView signupView){
        this.signupView = signupView;
        handler =  new android.os.Handler(Looper.getMainLooper());
        userBiz = new UserBiz();
    }

    @Override
    public void doSignUp(Student student, String password) {
        userBiz.signup(student, password, new IUserBiz.OnSignupListener() {
            @Override
            public void signupSuccess(final Student student) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        signupView.onSignupSuccess(student);
                    }
                });
            }

            @Override
            public void signupFail(int code) {

            }
        });
    }
}
