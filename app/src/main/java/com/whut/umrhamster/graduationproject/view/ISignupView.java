package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Student;

public interface ISignupView {
    void onSignupSuccess(Student student);
    void onSignupFail(int code);
}
