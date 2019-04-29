package com.whut.umrhamster.graduationproject.model.biz;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.HistoryService;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBiz implements IHistoryBiz {
    @Override
    public void getAllHistory(int start, int studentId,  int type, OnHistoryListener onHistoryListener) {
        RetrofitUtil.getHistory(start,studentId,type,onHistoryListener);
    }

    @Override
    public void isHistoryExist(int studentId, int type, int contentId, final OnHistoryExistListener onHistoryExistListener) {
        HistoryService service = RetrofitUtil.retrofit.create(HistoryService.class);
        Call<HttpData> call = service.isHistoryExist(studentId,type,contentId,true);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null){
                    onHistoryExistListener.existResult(data.getCode());
                }else {
                    onHistoryExistListener.existResult(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onHistoryExistListener.existResult(-1);
            }
        });
    }

    @Override
    public void insertHistory(int studentId, int type, int contentId, int watchedTime, final OnHistoryInsertListener onHistoryInsertListener) {
        HistoryService service = RetrofitUtil.retrofit.create(HistoryService.class);
        Call<HttpData> call = service.insertHistory(studentId, type, contentId, watchedTime);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null){
                    onHistoryInsertListener.insertResult(data.getCode());
                }else {
                    onHistoryInsertListener.insertResult(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onHistoryInsertListener.insertResult(-1);
            }
        });
    }

    @Override
    public void deleteHistoryByIds(int student, int type, HashSet<Integer> ids, final OnHistoryDeleteListener onHistoryDeleteListener) {
        HistoryService service = RetrofitUtil.retrofit.create(HistoryService.class);
        Call<HttpData> call = service.deleteHistoryByIds(student,type,ids);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null){
                    onHistoryDeleteListener.deleteResult(data.getCode());
                }else {
                    onHistoryDeleteListener.deleteResult(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onHistoryDeleteListener.deleteResult(-1);
            }
        });
    }

    @Override
    public void updateHistory(int student, int type, int contentId, int watchedTime, final OnHistoryUpdateListener onHistoryUpdateListener) {
        HistoryService service = RetrofitUtil.retrofit.create(HistoryService.class);
        Call<HttpData> call = service.updateHistory(student,type,contentId,watchedTime);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {
                HttpData data = response.body();
                if (data != null){
                    onHistoryUpdateListener.updateResult(data.getCode());
                }else {
                    onHistoryUpdateListener.updateResult(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {
                onHistoryUpdateListener.updateResult(-1);
            }
        });
    }

}
