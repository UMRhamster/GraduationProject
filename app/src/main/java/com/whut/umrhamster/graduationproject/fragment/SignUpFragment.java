package com.whut.umrhamster.graduationproject.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.whut.umrhamster.graduationproject.LoginActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ISignupPresenter;
import com.whut.umrhamster.graduationproject.presenter.IVerifyPresenter;
import com.whut.umrhamster.graduationproject.presenter.SignupPresenterCompl;
import com.whut.umrhamster.graduationproject.presenter.VerifyPresenter;
import com.whut.umrhamster.graduationproject.utils.verification.VerifyUtil;
import com.whut.umrhamster.graduationproject.view.ISignupView;
import com.whut.umrhamster.graduationproject.view.IVerifyView;
import com.whut.umrhamster.graduationproject.view.TextDrawable;

public class SignUpFragment extends Fragment implements IVerifyView {

    private TextInputEditText inputAccount;     //用户名输入框
    private TextInputEditText inputPassword;    //密  码输入框

    private ImageView imageViewBack;

    private TextView textViewErrorAccount;
    private TextView textViewErrorPassword;

    private CircularProgressButton cpbSignUp;
    private boolean isLogining;
    private boolean isVerify;

    IVerifyPresenter iVerifyPresenter;
    private int verifyCode = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_signup,container,false);
        initView(view);
        initEvent();
        return view;
    }

    public void initView(View view){
        inputAccount = view.findViewById(R.id.signup_til_tiet_username);
        inputPassword = view.findViewById(R.id.signup_til_tiet_password);
        imageViewBack = view.findViewById(R.id.signup_tb_rl_iv_back);
        textViewErrorAccount = view.findViewById(R.id.signup_til_tv_username);
        textViewErrorPassword = view.findViewById(R.id.signup_til_tv_password);
        cpbSignUp = view.findViewById(R.id.signup_cpb_signup);
        cpbSignUp.setIndeterminateProgressMode(true);


        TextDrawable textDrawable = new TextDrawable(getActivity());
        textDrawable.setText("| 获取验证码");
        textDrawable.setTextSize(16);
        textDrawable.setTextColor(getResources().getColor(R.color.themeColor));
        textDrawable.setBounds(0,0,textDrawable.getMinimumWidth(),textDrawable.getMinimumHeight());
        inputPassword.setCompoundDrawables(null,null,textDrawable,null);
        isLogining = false;
        isVerify = false;

        iVerifyPresenter = new VerifyPresenter(this);
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

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.loginWithPwFree();
            }
        });

        cpbSignUp.setOnClickListener(new View.OnClickListener() {
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
                    if (verifyCode == Integer.valueOf(inputPassword.getText().toString())){
                        Student student = new Student();
                        student.setEmail(inputAccount.getText().toString());
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        SignUpPasswordFragment passwordFragment = new SignUpPasswordFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("student",student);
                        passwordFragment.setArguments(bundle);
                        manager.beginTransaction().addToBackStack(null).add(R.id.ac_login_fl,passwordFragment).commit();
                    }else {
                        Toast.makeText(getActivity(),"验证码错误",Toast.LENGTH_SHORT).show();
                    }
                    isLogining = false;
//                    iSignupPresenter.doSignUp(nickname,username,password);
                }
            }
        });

    }

    @Override
    public void onVerification(int code) {
        verifyCode = code;
    }
}
