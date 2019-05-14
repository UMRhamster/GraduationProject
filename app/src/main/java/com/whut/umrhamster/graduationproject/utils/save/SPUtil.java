package com.whut.umrhamster.graduationproject.utils.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whut.umrhamster.graduationproject.model.bean.Student;

import java.lang.reflect.Type;

public class SPUtil {
    //使用sharePreference存储数据
    public static void saveData(Context context, String name, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    //从sharePreference中读取数据
    public static String loadData(Context context, String name, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"#");
    }

    public static void saveStudent(Context context, Student student){
        SharedPreferences sharedPreferences = context.getSharedPreferences("student",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(student);
        editor.putString("student_info",json);
        editor.apply();
    }

    public static Student loadStudent(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("student",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("student_info",null);
        Type type = new TypeToken<Student>(){}.getType();
        return gson.fromJson(json,type);
    }

    public static void clearStudent(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("student",Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
