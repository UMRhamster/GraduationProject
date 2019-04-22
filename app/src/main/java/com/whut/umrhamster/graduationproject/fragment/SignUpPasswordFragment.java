package com.whut.umrhamster.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.view.IInitWidgetView;

public class SignUpPasswordFragment extends Fragment implements IInitWidgetView {
    private TextInputEditText tietPassword;
    private TextInputEditText tietPasswordConfirm;

    private CircularProgressButton cpbNext;

    private boolean isSignup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_signup_password,container,false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        cpbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSignup){
                    isSignup = true;
                    if (!tietPassword.getText().toString().equals(tietPassword.getText().toString())){
                        Toast.makeText(getActivity(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                        isSignup = false;
                        return;
                    }
                    Student student = getArguments().getParcelable("student");
//                    Log.d("test",student.getEmail());
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    SignUpNicknameFragment nicknameFragment = new SignUpNicknameFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("student",student);
                    bundle.putString("password",tietPasswordConfirm.getText().toString());
                    nicknameFragment.setArguments(bundle);
                    manager.beginTransaction().addToBackStack(null).add(R.id.ac_login_fl,nicknameFragment).commit();
                    isSignup = false;
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        tietPassword = view.findViewById(R.id.signup_password_til_tiet_username);
        tietPasswordConfirm = view.findViewById(R.id.signup_password_til_tiet_password);
        cpbNext = view.findViewById(R.id.signup_password_cpb_signup);

        isSignup = false;
    }
}
