package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.Watch;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.WatchService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchBiz implements IWatchBiz {
    @Override
    public void getWatchByStudentId(int studentId, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);;
        Call<HttpData<List<Watch>>> call = service.getWatchByStudentId(studentId);
        call.enqueue(new Callback<HttpData<List<Watch>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Watch>>> call, Response<HttpData<List<Watch>>> response) {
                HttpData<List<Watch>> data = response.body();
                if (data != null){
                    onWatchListener.onSuccess(data.getData());
                }else {
                    onWatchListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Watch>>> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }

    @Override
    public void getWatchLimit20(int studentId, int start, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData<List<Watch>>> call = service.getWatchLimit20(studentId,start);
        call.enqueue(new Callback<HttpData<List<Watch>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Watch>>> call, Response<HttpData<List<Watch>>> response) {
                HttpData<List<Watch>> data = response.body();
                if (data != null){
                    onWatchListener.onSuccess(data.getData());
                }else {
                    onWatchListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Watch>>> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }

    @Override
    public void addWatch(int studentId, int teacherId, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData> call = service.addWatch(studentId,teacherId);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                onWatchListener.onSuccess(null);
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }

    @Override
    public void isWatchExist(int studentId, int teacherId, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData> call = service.isWatchExist(true,studentId,teacherId);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null && data.getCode() == 2033){
                    onWatchListener.onSuccess(null);
                }else {
                    onWatchListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });

    }

    @Override
    public void deleteWatchById(int id, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData> call = service.deleteWatchById(id);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                onWatchListener.onSuccess(null);
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }

    @Override
    public void deleteWatchBySaT(int studentId, int teacherId, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData> call = service.deleteWatchBySaT(studentId,teacherId);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                onWatchListener.onSuccess(null);
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onWatchListener.onFail(-1);
            }
        });
    }

    @Override
    public void getNumOfWatch(int idf, int id, final OnWatchListener onWatchListener) {
        WatchService service = RetrofitUtil.retrofit.create(WatchService.class);
        Call<HttpData<List<Watch>>> call = null;
        if (idf == 1){
            call = service.getNumOfWatchTeacher(1,id);
        }else if (idf == 2){
            call = service.getNumOfWatchStudent(2,id);
        }
        if (call != null){
            call.enqueue(new Callback<HttpData<List<Watch>>>() {
                @Override
                public void onResponse(Call<HttpData<List<Watch>>> call, Response<HttpData<List<Watch>>> response) {
                    HttpData<List<Watch>> data = response.body();
                    if (data != null){
                        onWatchListener.onSuccess(data.getData());
                    }
                }

                @Override
                public void onFailure(Call<HttpData<List<Watch>>> call, Throwable t) {
                    onWatchListener.onFail(-1);
                }
            });
        }
    }
}
