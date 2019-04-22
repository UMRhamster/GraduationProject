package com.whut.umrhamster.graduationproject.utils.service;

import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.utils.http.HttpData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentService {
    @GET("student/{id}")
    Call<ResponseBody> getStudentById(@Path("id")int id);

    @GET("student")
    Call<ResponseBody> getStudentByUsernameAndPassword(@Query("username") String username, @Query("password") String password);

    @GET("student")
    Call<HttpData<Student>> getStudentByUsernameAndPasswordUseGson(@Query("username") String username, @Query("password") String password);

    @GET("student")
    Call<HttpData<Student>> getStudentByEmail(@Query("username") String email);

    @POST("student")
    Call<HttpData<Student>> signupStudent(@Query("username") String email, @Query("password") String password, @Query("nickname") String nickname);


}
