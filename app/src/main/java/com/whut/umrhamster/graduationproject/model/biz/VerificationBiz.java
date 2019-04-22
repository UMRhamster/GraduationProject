package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;

public class VerificationBiz implements IVerificationBiz {
    @Override
    public void emailVerification(String email, OnVerifyListener onVerifyListener) {
        RetrofitUtil.getVerificationFromEmail(email,onVerifyListener);
    }

    @Override
    public void phoneVerification(String phone, OnVerifyListener onVerifyListener) {

    }
}
