package com.whut.umrhamster.graduationproject.view;

public interface IVerifyView {
    void onVerification(int verifycode);
    void onVerifyFail(int code);
}
