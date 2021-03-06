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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragmentPwFree extends Fragment implements ILoginView,IVerifyView {
    private TextInputEditText inputAccount;     //用户名输入框
    private TextInputEditText inputPassword;    //密  码输入框

    private TextView textViewPwLogin;
    private TextView textViewSignUp;

    private TextView textViewErrorAccount;
    private TextView textViewErrorPassword;

    private Button cpbLogin;


    ILoginPresenter iLoginPresenter;
    IVerifyPresenter iVerifyPresenter;


    private boolean pwSee;
    private boolean isLogining;
    private boolean isVerify;

    private int verification = -1;
    //第三方登录
    private ImageView ivQQ; //qq
    private ImageView ivWeixin;
    Tencent tencent;
    private IUiListener  iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            try {
                JSONObject jsonObject = (JSONObject) o;
                tencent.setOpenId(jsonObject.getString("openid"));
                tencent.setAccessToken(jsonObject.getString("access_token"),jsonObject.getString("expires_in"));
                QQToken qqToken = tencent.getQQToken();
                UserInfo userInfo = new UserInfo(getActivity().getApplicationContext(),qqToken);
                userInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Log.d("信息",""+o.toString());
                        JSONObject object = (JSONObject) o;

                        try {
                            Picasso.with(getActivity()).load(((JSONObject) o).getString("figureurl_qq_1")).into(ivWeixin);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                Toast.makeText(getActivity(),"AA"+o.toString(),Toast.LENGTH_SHORT).show();
                Log.d("qq-login","complete");
                Log.d("qq-login","111"+o.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.d("qq-login","error");
        }

        @Override
        public void onCancel() {
            Log.d("qq-login","cancel");
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_login_pw_free,container,false);
        tencent = Tencent.createInstance("101560817",getActivity().getApplicationContext());
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
//        cpbLogin.setIndeterminateProgressMode(true);

        textViewPwLogin = view.findViewById(R.id.login_pw_free_tb_rl_tv_pw);
        textViewSignUp = view.findViewById(R.id.login_pw_free_rl_tv_signup);
        ivQQ = view.findViewById(R.id.login_pw_free_iv_qq);
        ivWeixin = view.findViewById(R.id.login_pw_free_iv_weixin);

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

    TextDrawable drawable;

    @SuppressLint("ClickableViewAccessibility")
    public void initEvent(){
        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawable = (TextDrawable) inputPassword.getCompoundDrawables()[2];
                if (drawable == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getX() > inputAccount.getWidth() - inputAccount.getPaddingEnd()-drawable.getIntrinsicWidth()){
                        if (!isVerify){
                            isVerify = true;
                            if (VerifyUtil.localEmailVerify(inputAccount.getText().toString())){
                                iVerifyPresenter.doEmailVerify(1,inputAccount.getText().toString());
                            }else {
                                Toast.makeText(getActivity(),"邮箱错误，请确认邮箱",Toast.LENGTH_SHORT).show();
                                isVerify = false;
                            }
//                            Toast.makeText(getActivity(),"已发送验证码",Toast.LENGTH_SHORT).show();
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
//                    cpbLogin.setProgress(0);
//                    cpbLogin.setProgress(50);
                    if (verification == Integer.valueOf(inputPassword.getText().toString())){
                        iLoginPresenter.doLoginWithVerification(inputAccount.getText().toString());
                    }else {
                        Toast.makeText(getActivity(),"验证码错误",Toast.LENGTH_SHORT).show();
//                        cpbLogin.setProgress(-1);
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

        ivQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tencent.isSessionValid()){
                    Log.d("test","ddddddddddddddddddd");
                    tencent.login(LoginFragmentPwFree.this, "get_user_info",iUiListener );
                }
                Log.d("test","aaaaaaaaaaaaaa");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,iUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSuccess(Student student) {
//        cpbLogin.setProgress(100);
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
//        cpbLogin.setProgress(-1);
        isLogining = !isLogining;
    }

    @Override
    public void onVerification(int verifycode) {
        verification = verifycode;
        CountDownTimer timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) (millisUntilFinished/1000);
                if (drawable != null){
                    if (second <= 1){
                        drawable.setText("| 获取验证码");
                        isVerify = false;
                    }else{
                        drawable.setText("| "+second+"秒后重发");
                    }
                }else {
                    isVerify = false;
                }
            }

            @Override
            public void onFinish() {
                isVerify = false;
            }
        };
        timer.start();
    }

    @Override
    public void onVerifyFail(int code) {
        if (code == 2043){
            Toast.makeText(getActivity(),"此邮箱未注册",Toast.LENGTH_SHORT).show();
        }else if (code == 2044){
            Toast.makeText(getActivity(),"邮箱错误，请确认邮箱",Toast.LENGTH_SHORT).show();
        }
        isVerify = false;
    }
}
