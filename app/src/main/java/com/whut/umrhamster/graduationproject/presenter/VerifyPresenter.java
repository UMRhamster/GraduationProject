package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.model.biz.IVerificationBiz;
import com.whut.umrhamster.graduationproject.model.biz.VerificationBiz;
import com.whut.umrhamster.graduationproject.view.ILoginView;
import com.whut.umrhamster.graduationproject.view.IVerifyView;

public class VerifyPresenter implements IVerifyPresenter {
    IVerifyView iVerifyView;
    Handler handler;
    IVerificationBiz iVerificationBiz;

    public VerifyPresenter(IVerifyView iVerifyView){
        this.iVerifyView = iVerifyView;
        handler = new Handler(Looper.getMainLooper());
        iVerificationBiz = new VerificationBiz();
    }

    @Override
    public void doEmailVerify(String email) {
        iVerificationBiz.emailVerification(email, new IVerificationBiz.OnVerifyListener() {
            @Override
            public void onVerifyResult(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iVerifyView.onVerification(code);
                    }
                });
            }
        });
    }

    @Override
    public void doPhoneVerify(String phone) {

    }
}
