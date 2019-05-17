package com.whut.umrhamster.graduationproject.presenter;

public interface IVerifyPresenter {
    void doEmailVerify(int type, String email);
    void doPhoneVerify(String phone);
}
