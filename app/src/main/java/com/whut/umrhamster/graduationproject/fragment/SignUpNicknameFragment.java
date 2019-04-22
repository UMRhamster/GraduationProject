package com.whut.umrhamster.graduationproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private CircularProgressButton cpbSave;

    ISignupPresenter signupPresenter;

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
                Student student = getArguments().getParcelable("student");
                student.setNickname(tietNickname.getText().toString());
                String password = getArguments().getString("password");
                signupPresenter.doSignUp(student,password);
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
        Toast.makeText(getActivity(),student.getNickname(),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(),MainActivity.class));
    }

    @Override
    public void onSignupFail(int code) {
        Toast.makeText(getActivity(),"注册失败",Toast.LENGTH_SHORT).show();
    }
}
