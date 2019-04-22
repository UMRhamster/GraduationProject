package com.whut.umrhamster.graduationproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.whut.umrhamster.graduationproject.LoginActivity;
import com.whut.umrhamster.graduationproject.MainActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ILoginPresenter;
import com.whut.umrhamster.graduationproject.presenter.IVerifyPresenter;
import com.whut.umrhamster.graduationproject.presenter.LoginPresenterCompl;
import com.whut.umrhamster.graduationproject.presenter.VerifyPresenter;
import com.whut.umrhamster.graduationproject.utils.http.NetworkUtil;
import com.whut.umrhamster.graduationproject.utils.verification.VerifyUtil;
import com.whut.umrhamster.graduationproject.view.ILoginView;
import com.whut.umrhamster.graduationproject.view.TextDrawable;

import com.whut.umrhamster.graduationproject.utils.verification.VerifyUtil.VerifyStatus;


public class LoginFragmentPw extends Fragment implements ILoginView {
    private TextInputEditText inputAccount;     //用户名输入框
    private TextInputEditText inputPassword;    //密  码输入框
    private ImageView imageViewBack;

    private TextView textViewErrorAccount;
    private TextView textViewErrorPassword;

    private CircularProgressButton cpbLogin;
    private TextView textViewForget;


    ILoginPresenter iLoginPresenter;


    private boolean pwSee;
    private boolean isLogining;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_login_pw,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initView(View view){
        inputAccount = view.findViewById(R.id.login_pw_til_tiet_username);
        inputPassword = view.findViewById(R.id.login_pw_til_tiet_password);
        imageViewBack = view.findViewById(R.id.login_pw_tb_rl_iv_back);

        textViewForget = view.findViewById(R.id.login_pw_rl_tv_forget);

        textViewErrorAccount = view.findViewById(R.id.login_pw_til_tv_username);
        textViewErrorPassword = view.findViewById(R.id.login_pw_til_tv_password);

        cpbLogin = view.findViewById(R.id.login_pw_cpb_login);
        cpbLogin.setIndeterminateProgressMode(true);

        iLoginPresenter = new LoginPresenterCompl(this);

        pwSee = false;
        isLogining = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initEvent(){
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = inputPassword.getCompoundDrawables()[2];
                if (drawable == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getX() > inputAccount.getWidth() - inputAccount.getPaddingEnd()-drawable.getIntrinsicWidth()){
                        if (!pwSee){
                            Drawable right = getResources().getDrawable(R.drawable.pw_see);
                            right.setBounds(0,0,right.getMinimumWidth(),right.getMinimumHeight());
                            inputPassword.setCompoundDrawables(null,null,right,null);
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            pwSee = true;
                        }else {
                            Drawable right = getResources().getDrawable(R.drawable.pw_no_see);
                            right.setBounds(0,0,right.getMinimumWidth(),right.getMinimumHeight());
                            inputPassword.setCompoundDrawables(null,null,right,null);
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            pwSee = false;
                        }
                    }
                }
                return false;
            }
        });

        cpbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogining){
                    isLogining = !isLogining;
                    textViewErrorAccount.setText("");
                    textViewErrorPassword.setText("");
                    String username = inputAccount.getText().toString();
                    String password = inputPassword.getText().toString();
                    boolean doLogin = true;
                    if (username.isEmpty()){
                        doLogin = false;
                        textViewErrorAccount.setText("手机/邮箱非法");
                    }
                    if (password.isEmpty()){
                        doLogin = false;
                        textViewErrorPassword.setText("密码非法");
                    }
                    if (!doLogin){
                        isLogining = false;
                        return;
                    }
                    if (!NetworkUtil.checkNetState(getActivity())){
                        isLogining = false;
                        Toast.makeText(getActivity(),"请求出错，请检查网络",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cpbLogin.setProgress(0);
                    cpbLogin.setProgress(50);
                    iLoginPresenter.doLogin(inputAccount.getText().toString(),inputPassword.getText().toString());
                }
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.loginWithPwFree();
            }
        });

        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击忘记密码",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccess(Student student) {
        Toast.makeText(getActivity(),student.getNickname(),Toast.LENGTH_SHORT).show();
        cpbLogin.setProgress(100);
        isLogining = !isLogining;
        startActivity(new Intent(getActivity(),MainActivity.class));
    }

    @Override
    public void onFail(int code) {
        if (code == 0){
            Toast.makeText(getActivity(),"连接服务器失败，请重新尝试",Toast.LENGTH_SHORT).show();
        }else if (code == 1){
            Toast.makeText(getActivity(),"服务器异常,请重新尝试",Toast.LENGTH_SHORT).show();
        }else if (code == 2){
            Toast.makeText(getActivity(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
        cpbLogin.setProgress(-1);
        isLogining = !isLogining;
    }
}
