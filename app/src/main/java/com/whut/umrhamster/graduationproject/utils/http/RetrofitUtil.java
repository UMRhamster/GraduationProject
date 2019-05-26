package com.whut.umrhamster.graduationproject.utils.http;

import android.util.Log;

import com.whut.umrhamster.graduationproject.model.bean.History;
import com.whut.umrhamster.graduationproject.model.bean.Live;
import com.whut.umrhamster.graduationproject.model.bean.Student;
import com.whut.umrhamster.graduationproject.model.bean.Verification;
import com.whut.umrhamster.graduationproject.model.biz.IHistoryBiz;
import com.whut.umrhamster.graduationproject.model.biz.ILiveBiz;
import com.whut.umrhamster.graduationproject.model.biz.IUserBiz;
import com.whut.umrhamster.graduationproject.model.biz.IVerificationBiz;
import com.whut.umrhamster.graduationproject.utils.service.HistoryService;
import com.whut.umrhamster.graduationproject.utils.service.LiveService;
import com.whut.umrhamster.graduationproject.utils.service.PhotoService;
import com.whut.umrhamster.graduationproject.utils.service.StudentService;
import com.whut.umrhamster.graduationproject.utils.service.VerificationService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public  static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.233:8080/api/v1/")
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

    //向指定邮箱发送邮件验证码
    public static void getVerificationFromEmail(int type, String email, final IVerificationBiz.OnVerifyListener onVerifyListener){
        VerificationService service = retrofit.create(VerificationService.class);
        Call<HttpData<Verification>> call = service.getVerificationFromEmail(type,email);
        call.enqueue(new Callback<HttpData<Verification>>() {
            @Override
            public void onResponse(Call<HttpData<Verification>> call, Response<HttpData<Verification>> response) {
                HttpData<Verification> data = response.body();
                if (data != null){
                    if (data.getData() != null){
                        onVerifyListener.onVerifySuccess(data.getData().getVerifycode());//返回6位验证码
                    }else {
                        onVerifyListener.onVerifyFail(data.getCode()); //错误状态码，2043-邮箱未注册，2044邮箱错误
                    }
                }else {
                    onVerifyListener.onVerifyFail(-1);
                }
            }

            @Override
            public void onFailure(Call<HttpData<Verification>> call, Throwable t) {
                onVerifyListener.onVerifyFail(-1);
            }
        });
    }

    //通过邮箱直接获取学员信息，前提需要进行邮件验证
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

    /*
     *获取历史记录
     *@Param:studenId
     */
    //根据学员id获取所有历史记录(包含视频与直播)
    public static void getHistory(int start, int studentId, int type, final IHistoryBiz.OnHistoryListener onHistoryListener){
        HistoryService service = retrofit.create(HistoryService.class);
        Call<HttpData<List<History>>> call = service.getHistory(start,studentId,type);
        call.enqueue(new Callback<HttpData<List<History>>>() {
            @Override
            public void onResponse(Call<HttpData<List<History>>> call, Response<HttpData<List<History>>> response) {
                HttpData<List<History>> data = response.body();
//                Log.d("dsa","aaaa"+data.getMsg());
                if (data != null){
                    onHistoryListener.onHistorySuccess(data.getData());
                }else {
//                    Log.d("dsa","aaaa"+data.getMsg());
                    onHistoryListener.onHistoryFail(0);
//                    Log.d("dsa","aaaa"+data.getMsg());
                }
            }

            @Override
            public void onFailure(Call<HttpData<List<History>>> call, Throwable t) {
                t.printStackTrace();
                onHistoryListener.onHistoryFail(0);
            }
        });
    }

    public static void uploadFilr(String filePath){
        File file = new File(filePath);
        RequestBody  requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),filePath);
        MultipartBody.Part mFile = MultipartBody.Part.createFormData("file",file.getName(),requestFile);
        PhotoService service = retrofit.create(PhotoService.class);
        Call<HttpData> call = service.uploadPhoto(1,mFile);
        call.enqueue(new Callback<HttpData>() {
            @Override
            public void onResponse(Call<HttpData> call, Response<HttpData> response) {

            }

            @Override
            public void onFailure(Call<HttpData> call, Throwable t) {

            }
        });
    }
}
