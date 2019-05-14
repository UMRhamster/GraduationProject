package com.whut.umrhamster.graduationproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.verification.VerifyUtil;
import com.whut.umrhamster.graduationproject.view.ILoginView;
import com.whut.umrhamster.graduationproject.view.IVerifyView;
import com.whut.umrhamster.graduationproject.view.TextDrawable;

public class LoginFragmentPwFree extends Fragment implements ILoginView,IVerifyView {
    private TextInputEditText inputAccount;     //用户名输入框
    private TextInputEditText inputPassword;    //密  码输入框

    private TextView textViewPwLogin;
    private TextView textViewSignUp;

    private TextView textViewErrorAccount;
    private TextView textViewErrorPassword;

    private CircularProgressButton cpbLogin;


    ILoginPresenter iLoginPresenter;
    IVerifyPresenter iVerifyPresenter;


    private boolean pwSee;
    private boolean isLogining;
    private boolean isVerify;

    private int verification = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_login_pw_free,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initView(View view){
        inputAccount = view.findViewById(R.id.login_pw_free_til_tiet_username);
        inputPassword = view.findViewById(R.id.login_pw_free_til_tiet_password);

        textViewErrorAccount = view.findViewById(R.id.login_pw_free_til_tv_username);
        textViewErrorPassword = view.findViewById(R.id.login_pw_free_til_tv_password);

        cpbLogin = view.findViewById(R.id.login_pw_free_cpb_login);
        cpbLogin.setIndeterminateProgressMode(true);

        textViewPwLogin = view.findViewById(R.id.login_pw_free_tb_rl_tv_pw);
        textViewSignUp = view.findViewById(R.id.login_pw_free_rl_tv_signup);

        TextDrawable textDrawable = new TextDrawable(getActivity());
        textDrawable.setText("| 获取验证码");
        textDrawable.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        textDrawable.setTextSize(16);
        textDrawable.setTextColor(getResources().getColor(R.color.themeColor));
        textDrawable.setBounds(0,0,textDrawable.getMinimumWidth(),textDrawable.getMinimumHeight());
        inputPassword.setCompoundDrawables(null,null,textDrawable,null);

        iLoginPresenter = new LoginPresenterCompl(this);
        iVerifyPresenter = new VerifyPresenter(this);

        pwSee = false;
        isLogining = false;
        isVerify = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initEvent(){
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final TextDrawable drawable = (TextDrawable) inputPassword.getCompoundDrawables()[2];
                if (drawable == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getX() > inputAccount.getWidth() - inputAccount.getPaddingEnd()-drawable.getIntrinsicWidth()){
                        if (!isVerify){
                            isVerify = true;
                            iVerifyPresenter.doEmailVerify(inputAccount.getText().toString());
                            Toast.makeText(getActivity(),"已发送验证码",Toast.LENGTH_SHORT).show();
                            CountDownTimer timer = new CountDownTimer(60000,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    int second = (int) (millisUntilFinished/1000);
                                    if (second <= 1){
                                        drawable.setText("| 获取验证码");
                                        isVerify = false;
                                    }else{
                                        drawable.setText("| "+second+"秒后重发");
                                    }
                                }

                                @Override
                                public void onFinish() {

                                }
                            };
                            timer.start();
                        }
                    }
                }
                return false;
            }
        });

        textViewPwLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"asdada",Toast.LENGTH_SHORT).show();
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.loginWithPw();
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
                        textViewErrorPassword.setText("验证码非法");
                    }
                    if (!doLogin){
                        isLogining = false;
                        return;
                    }
                    cpbLogin.setProgress(0);
                    cpbLogin.setProgress(50);
                    if (verification == Integer.valueOf(inputPassword.getText().toString())){
                        iLoginPresenter.doLoginWithVerification(inputAccount.getText().toString());
                    }else {
                        Toast.makeText(getActivity(),"验证码错误",Toast.LENGTH_SHORT).show();
                        cpbLogin.setProgress(-1);
                        isLogining = false;
                    }
//                    iLoginPresenter.doLogin(inputAccount.getText().toString(),inputPassword.getText().toString());
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.signUp();
            }
        });
    }


    @Override
    public void onSuccess(Student student) {
        cpbLogin.setProgress(100);
        isLogining = !isLogining;
        Intent intent = new Intent();
        intent.putExtra("student",student);
        getActivity().setResult(1,intent);
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null){
            manager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
        }
        getActivity().finish();
//        startActivity(new Intent(getActivity(),MainActivity.class));
    }

    @Override
    public void onFail(int code) {
        if (code == 0){
            Toast.makeText(getActivity(),"连接服务器失败",Toast.LENGTH_SHORT).show();
        }else if (code == 1){
            Toast.makeText(getActivity(),"服务器异常",Toast.LENGTH_SHORT).show();
        }
        cpbLogin.setProgress(-1);
        isLogining = !isLogining;
    }

    @Override
    public void onVerification(int code) {
        verification = code;
    }
}
