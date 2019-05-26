package com.whut.umrhamster.graduationproject.presenter;

import android.os.Handler;
import android.os.Looper;

import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.model.biz.ITeacherBiz;
import com.whut.umrhamster.graduationproject.model.biz.TeacherBiz;
import com.whut.umrhamster.graduationproject.view.ITeacherView;

import java.util.List;

public class TeacherPresenter implements ITeacherPresenter {
    private ITeacherBiz teacherBiz;
    private ITeacherView teacherView;
    private Handler handler;

    public TeacherPresenter(ITeacherView teacherView){
        this.teacherView = teacherView;
        this.teacherBiz = new TeacherBiz();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void doGetTeacherByKeyword(String keyword) {
        teacherBiz.getTeacherByKeyord(keyword, new ITeacherBiz.OnTeacherListener() {
            @Override
            public void onSuccess(final List<Teacher> teacherList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherView.onGetKeywordTeacherSuccess(teacherList);
                    }
                });
            }

            @Override
            public void onFail(final int code) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherView.onTeacherFail(code);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        teacherView=null;
    }
}
