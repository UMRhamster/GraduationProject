package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Teacher;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.TeacherService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherBiz implements ITeacherBiz {
    @Override
    public void getTeacherByKeyord(String keyword, final OnTeacherListener onTeacherListener) {
        TeacherService service = RetrofitUtil.retrofit.create(TeacherService.class);
        Call<HttpData<List<Teacher>>> call = service.getTeacheByKeyword(keyword);
        call.enqueue(new Callback<HttpData<List<Teacher>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Teacher>>> call, Response<HttpData<List<Teacher>>> response) {
                HttpData<List<Teacher>> data = response.body();
                if (data != null){
                    onTeacherListener.onSuccess(data.getData());
                }else {
                    onTeacherListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Teacher>>> call, Throwable t) {
                onTeacherListener.onFail(-1);
            }
        });

    }
}
