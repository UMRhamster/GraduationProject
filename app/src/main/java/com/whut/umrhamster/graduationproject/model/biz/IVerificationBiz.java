package com.whut.umrhamster.graduationproject.model.biz;

public interface IVerificationBiz {
    void emailVerification(String email, OnVerifyListener onVerifyListener);
    void phoneVerification(String phone, OnVerifyListener onVerifyListener);

    public interface OnVerifyListener {
        void onVerifyResult(int code);
    }

}
