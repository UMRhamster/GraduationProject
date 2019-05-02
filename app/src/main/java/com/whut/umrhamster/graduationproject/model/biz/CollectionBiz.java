package com.whut.umrhamster.graduationproject.model.biz;

import android.util.Log;

import com.whut.umrhamster.graduationproject.model.bean.Collection;
import com.whut.umrhamster.graduationproject.model.bean.Video;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;
import com.whut.umrhamster.graduationproject.utils.http.RetrofitUtil;
import com.whut.umrhamster.graduationproject.utils.service.CollectionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionBiz implements ICollectionBiz {
    @Override
    public void getCollectionById(int studentId, final OnCollectionListener onCollectionListener) {
        CollectionService service = RetrofitUtil.retrofit.create(CollectionService.class);
        Call<HttpData<List<Collection>>> call = service.getCollectionById(studentId);
        call.enqueue(new Callback<HttpData<List<Collection>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Collection>>> call, Response<HttpData<List<Collection>>> response) {
                Log.d("dsa",response.message());
                HttpData<List<Collection>> data = response.body();
                if (data != null){
                    onCollectionListener.onSuccess(data.getData());
                }else {
                    onCollectionListener.onFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Collection>>> call, Throwable t) {
                t.printStackTrace();
                onCollectionListener.onFail(-1);
            }
        });

    }
}
