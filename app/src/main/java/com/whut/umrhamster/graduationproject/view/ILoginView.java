package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Student;

public interface ILoginView {
    void onSuccess(Student student);
    void onFail(int code);
}
