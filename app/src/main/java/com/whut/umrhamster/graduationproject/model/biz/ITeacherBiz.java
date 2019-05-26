package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Teacher;

import java.util.List;

public interface ITeacherBiz {
    void getTeacherByKeyord(String keyword,OnTeacherListener onTeacherListener);

    public interface OnTeacherListener{
        void onSuccess(List<Teacher> teacherList);
        void onFail(int code);
    }
}
