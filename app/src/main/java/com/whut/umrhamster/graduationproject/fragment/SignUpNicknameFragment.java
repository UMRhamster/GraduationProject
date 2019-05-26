package com.whut.umrhamster.graduationproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.whut.umrhamster.graduationproject.MainActivity;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.presenter.ISignupPresenter;
import com.whut.umrhamster.graduationproject.presenter.SignupPresenterCompl;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;
import com.whut.umrhamster.graduationproject.view.ISignupView;

public class SignUpNicknameFragment extends Fragment implements IInitWidgetView,ISignupView {
    private TextInputEditText tietNickname;
    private Button cpbSave;

    ISignupPresenter signupPresenter;

    private boolean isSignUp = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_signup_nickname,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        cpbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSignUp){
                    isSignUp = true;
                    if (tietNickname.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(),"昵称不能为空",Toast.LENGTH_SHORT).show();
                        isSignUp = false;
                    }else {
                        Student student = getArguments().getParcelable("student");
                        student.setNickname(tietNickname.getText().toString());
                        String password = getArguments().getString("password");
                        signupPresenter.doSignUp(student,password);
                    }
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        tietNickname = view.findViewById(R.id.signup_nickname_til_tiet_username);
        cpbSave = view.findViewById(R.id.signup_nickname_cpb_signup);

        signupPresenter = new SignupPresenterCompl(this);
    }

    @Override
    public void onSignupSuccess(Student student) {
        isSignUp = false;
        Intent intent = new Intent();
        intent.putExtra("student",student);
        getActivity().setResult(1,intent);
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null){
            manager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//            manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
        }

        getActivity().finish();
//        Toast.makeText(getActivity(),student.getNickname(),Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(getActivity(),MainActivity.class));
    }

    @Override
    public void onSignupFail(int code) {
        isSignUp = false;
        Toast.makeText(getActivity(),"注册失败",Toast.LENGTH_SHORT).show();
    }
}
