package com.whut.umrhamster.graduationproject.model.biz;

public interface IVerificationBiz {
    void emailVerification(int type, String email, OnVerifyListener onVerifyListener);
    void phoneVerification(String phone, OnVerifyListener onVerifyListener);

    public interface OnVerifyListener {
        void onVerifySuccess(int verifycode);
        void onVerifyFail(int code);
    }

}
