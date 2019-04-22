package com.whut.umrhamster.graduationproject.presenter;

public interface ILoginPresenter {
    void doLogin(String userName, String userPassword);
    void doLoginWithVerification(String email);
}
