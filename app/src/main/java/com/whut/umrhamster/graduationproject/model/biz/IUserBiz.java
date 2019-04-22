package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Student;

public interface IUserBiz {
    void login(String userName, String userPassword, OnLoginListener onLoginListener);

    void loginWithoutPassword(String email, OnLoginListener onLoginListener);

    void signup(Student student, String password, OnSignupListener onSignupListener);

    interface OnLoginListener{
        void onLoginSuccess(Student student);
        void onLoginFail(int code);
    }

    interface OnSignupListener{
        void signupSuccess(Student student);
        void signupFail(int code);
    }
}
