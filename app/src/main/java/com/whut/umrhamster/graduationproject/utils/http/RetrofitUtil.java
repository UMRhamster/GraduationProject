package com.whut.umrhamster.graduationproject.utils.http;

import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Verification;
import com.whut.umrhamster.graduationproject.model.biz.ILiveBiz;
import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.model.biz.IVerificationBiz;
import com.whut.umrhamster.graduationproject.utils.service.LiveService;
import com.whut.umrhamster.graduationproject.utils.service.StudentService;
import com.whut.umrhamster.graduationproject.utils.service.VerificationService;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public  static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.106:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static void login(String username, String password, final IUserBiz.OnLoginListener onLoginListener){
        StudentService studentService = retrofit.create(StudentService.class);
        Call<HttpData<Student>> call = studentService.getStudentByUsernameAndPasswordUseGson(username,password);
        call.enqueue(new Callback<HttpData<Student>>() {
            @Override
            public void onResponse(Call<HttpData<Student>> call, Response<HttpData<Student>> response) {
               HttpData<Student> data = response.body();
               if (data == null){
                   onLoginListener.onLoginFail(1);
               }else {
                   Student student = data.getData();
                   if (student != null){
                       onLoginListener.onLoginSuccess(data.getData());
                   }else {
                       onLoginListener.onLoginFail(2);
                   }
               }
            }

            @Override
            public void onFailure(Call<HttpData<Student>> call, Throwable t) {
                onLoginListener.onLoginFail(0);   //服务器连接失败
            }
        });
    }

    public static void getVerificationFromEmail(String email, final IVerificationBiz.OnVerifyListener onVerifyListener){
        VerificationService service = retrofit.create(VerificationService.class);
        Call<HttpData<Verification>> call = service.getVerificationFromEmail(email);
        call.enqueue(new Callback<HttpData<Verification>>() {
            @Override
            public void onResponse(Call<HttpData<Verification>> call, Response<HttpData<Verification>> response) {
                Verification verification = response.body().getData();
                if (verification != null){
                    onVerifyListener.onVerifyResult(verification.getVerifycode());
                }
            }

            @Override
            public void onFailure(Call<HttpData<Verification>> call, Throwable t) {

            }
        });
    }

    public static void loginWithoutPassword(String email, final IUserBiz.OnLoginListener onLoginListener){
        StudentService service = retrofit.create(StudentService.class);
        Call<HttpData<Student>> call = service.getStudentByEmail(email);
        call.enqueue(new Callback<HttpData<Student>>() {
            @Override
            public void onResponse(Call<HttpData<Student>> call, Response<HttpData<Student>> response) {
                HttpData<Student> data = response.body();
                if (data == null){
                    onLoginListener.onLoginFail(1);
                }else {
                    Student student = data.getData();
                    if (student != null){
                        onLoginListener.onLoginSuccess(data.getData());
                    }else {
                        onLoginListener.onLoginFail(2);
                    }
                }
            }

            @Override
            public void onFailure(Call<HttpData<Student>> call, Throwable t) {
                onLoginListener.onLoginFail(0);   //服务器连接失败
            }
        });
    }

    public static void signup(Student student, String password, final IUserBiz.OnSignupListener onSignupListener){
        StudentService service = retrofit.create(StudentService.class);
        Call<HttpData<Student>> call = service.signupStudent(student.getEmail(),password,student.getNickname());
        call.enqueue(new Callback<HttpData<Student>>() {
            @Override
            public void onResponse(Call<HttpData<Student>> call, Response<HttpData<Student>> response) {
                HttpData<Student> data = response.body();
                if (data != null){
                    onSignupListener.signupSuccess(data.getData());
                }else {
                    onSignupListener.signupFail(1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<Student>> call, Throwable t) {
                onSignupListener.signupFail(1);
            }
        });
    }

    public static void getAllLive(boolean all, final ILiveBiz.OnLiveListener onLiveListener){
        LiveService service = retrofit.create(LiveService.class);
        Call<HttpData<List<Live>>> call = service.getAllLive(true);
        call.enqueue(new Callback<HttpData<List<Live>>>() {
            @Override
            public void onResponse(Call<HttpData<List<Live>>> call, Response<HttpData<List<Live>>> response) {
                HttpData<List<Live>> data = response.body();
                if (data != null){
                    onLiveListener.onLiveSuccess(data.getData());
                }else {
                    onLiveListener.onLiveFail(1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<Live>>> call, Throwable t) {
                onLiveListener.onLiveFail(1);
            }
        });
    }
}
