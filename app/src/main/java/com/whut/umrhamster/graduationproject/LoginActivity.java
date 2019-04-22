package com.whut.umrhamster.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.whut.umrhamster.graduationproject.animators.AnimatorUtils;
import com.whut.umrhamster.graduationproject.fragment.LoginFragmentPw;
import com.whut.umrhamster.graduationproject.fragment.LoginFragmentPwFree;
import com.whut.umrhamster.graduationproject.fragment.SignUpFragment;
import com.whut.umrhamster.graduationproject.presenter.ILoginPresenter;
import com.whut.umrhamster.graduationproject.presenter.LoginPresenterCompl;
import com.whut.umrhamster.graduationproject.utils.other.AdaptionUtil;
import com.whut.umrhamster.graduationproject.utils.other.DrawableUtil;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightProvider;
import com.whut.umrhamster.graduationproject.view.CircleImageView;
import com.whut.umrhamster.graduationproject.view.ILoginView;
import com.whut.umrhamster.graduationproject.view.TextDrawable;

public class LoginActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    private LoginFragmentPw loginFragmentPw;
    private LoginFragmentPwFree loginFragmentPwFree;
    private SignUpFragment signUpFragment;

//    private TextInputEditText inputAccount;     //用户名输入框
//    private TextInputEditText inputPassword;    //密  码输入框
//    private TextInputLayout inputLayoutAccount;
//    private TextInputLayout inputLayoutPassword;
//
//    private ConstraintLayout constraintLayout;
//    private ImageView imageViewLogo;
//    private TextView textViewErrorAccount;
//    private TextView textViewErrorPassword;
//
//    private CircularProgressButton cpbLogin;

//    private PositionControl positionControl;
//    private int animationHeight; //Y轴平移动画的高度

//    private KeyboardHeightProvider keyboardHeightProvider;


//    ILoginPresenter iLoginPresenter;
//
//    private boolean isLogining;  //避免登录按钮重复点击触发登录响应
//    private boolean pwSee; //密码可见与否

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
//        AndroidBug5497Workaround.assistActivity(this);
        initView();
        initEvent();
        loginWithPwFree();

//        keyboardHeightProvider = new KeyboardHeightProvider(this);

//       constraintLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                keyboardHeightProvider.start();
//            }
//        });

        // Example of a call to a native method
    }


    private void initView(){
//        inputLayoutAccount = findViewById(R.id.aty_main_account_til);
//        inputLayoutPassword = findViewById(R.id.aty_main_password_til);
//        inputAccount = findViewById(R.id.aty_main_account_tiet);
//        inputPassword = findViewById(R.id.aty_main_password_tiet);
//        textViewErrorAccount = findViewById(R.id.aty_main_account_error_tv);
//        textViewErrorPassword = findViewById(R.id.aty_main_password_error_tv);
//
//        cpbLogin = findViewById(R.id.ac_main_login_cpb);
//        cpbLogin.setIndeterminateProgressMode(true);
//
//
//        constraintLayout = findViewById(R.id.aty_main_cl);
//        imageViewLogo = findViewById(R.id.aty_main_logo_iv);
//        positionControl = new PositionControl();
//        getViewsInitPosition(inputLayoutAccount,inputLayoutPassword,cpbLogin);

//        iLoginPresenter = new LoginPresenterCompl(this);
//        isLogining = false;
//        pwSee = false;

    }

    private void initEvent(){
//        cpbLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLogining){
//                    isLogining = !isLogining;
//                    cpbLogin.setProgress(0);
//                    cpbLogin.setProgress(50);
//                    iLoginPresenter.doLogin(inputAccount.getText().toString(),inputPassword.getText().toString());
//                }
//            }
//        });


    }
    public void loginWithPwFree(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fg_login_enter,R.anim.fg_login_exit,R.anim.fg_login_enter,R.anim.fg_login_exit);
        if (loginFragmentPwFree == null){
            loginFragmentPwFree = new LoginFragmentPwFree();
        }
        transaction.replace(R.id.ac_login_fl,loginFragmentPwFree);
        transaction.commit();
    }

    public void loginWithPw(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fg_login_enter,R.anim.fg_login_exit,R.anim.fg_login_enter,R.anim.fg_login_exit);
        if (loginFragmentPw == null){
            loginFragmentPw = new LoginFragmentPw();
        }
        transaction.addToBackStack(null);
        transaction.add(R.id.ac_login_fl,loginFragmentPw);
        transaction.commit();
    }

    public void signUp(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fg_login_enter,R.anim.fg_login_exit,R.anim.fg_login_enter,R.anim.fg_login_exit);
        if (signUpFragment == null){
            signUpFragment = new SignUpFragment();
        }
        transaction.addToBackStack(null);
        transaction.add(R.id.ac_login_fl,signUpFragment);
        transaction.commit();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();



//    @Override
//    public void onKeyboardHeightChanged(int height, int orientation, boolean status) {
//        Log.d("sdaaaaa",""+height);
//        if (status){
//            Log.d("屏幕可是高度变化","打开软键盘");
//            WindowManager wm = getWindowManager();
//            DisplayMetrics dm = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(dm);
//
//            animationHeight = height - dm.heightPixels + cpbLogin.getBottom() + AdaptionUtil.dp2px(this,8)+80;
//            Log.d("ddddddddd",""+animationHeight+"   "+dm.heightPixels+"   "+cpbLogin.getTop()+"  "+cpbLogin.getHeight()+" "+cpbLogin.getBottom());
//            AnimatorUtils.LoginAnimator(inputLayoutAccount,0-animationHeight);
//            AnimatorUtils.LoginAnimator(inputLayoutPassword,0-animationHeight);
//            AnimatorUtils.LoginAnimator(cpbLogin,0-animationHeight);
////            AnimatorUtils.LoginAnimator(imageViewLogo,0-animationHeight);
////            AnimatorUtils.AnimatorScale(imageViewLogo,1f,0.7f);
//        }else {
//            Log.d("屏幕可是高度变化","关闭软键盘");
//            AnimatorUtils.LoginAnimator(inputLayoutAccount,animationHeight);
//            AnimatorUtils.LoginAnimator(inputLayoutPassword,animationHeight);
//            AnimatorUtils.LoginAnimator(cpbLogin,animationHeight);
////            AnimatorUtils.LoginAnimator(imageViewLogo,animationHeight);
////            AnimatorUtils.AnimatorScale(imageViewLogo,0.7f,1f);
//        }
//    }

//    @Override
//    public void onLoginResult(boolean result, int code) {
//        if (result){
//            Snackbar.make(constraintLayout,"登陆成功",Snackbar.LENGTH_SHORT).show();
//            cpbLogin.setProgress(100);
//            isLogining = !isLogining;
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//        }else {
//            Snackbar.make(constraintLayout,"登陆失败",Snackbar.LENGTH_SHORT).show();
//            cpbLogin.setProgress(-1);
//            isLogining = !isLogining;
//        }
//    }
}
