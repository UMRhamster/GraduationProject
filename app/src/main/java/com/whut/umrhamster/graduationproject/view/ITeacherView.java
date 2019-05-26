package com.whut.umrhamster.graduationproject.view;

import com.whut.umrhamster.graduationproject.model.bean.Teacher;

import java.util.List;

public interface ITeacherView {
    void onGetKeywordTeacherSuccess(List<Teacher> teacherList);
    void onTeacherFail(int code);
}
