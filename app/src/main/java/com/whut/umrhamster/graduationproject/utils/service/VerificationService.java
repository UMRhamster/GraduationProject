package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Verification;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VerificationService {

    @GET("verification")  //待确认
    //type: 1-登录，2-注册
    Call<HttpData<Verification>> getVerificationFromEmail(@Query("type")int type,@Query("email") String email);
}