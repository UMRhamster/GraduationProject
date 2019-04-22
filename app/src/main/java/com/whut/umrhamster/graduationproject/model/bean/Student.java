package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    public int id;
    public String nickname;
    public String avatar;
    public String phone;
    public String email;



    public Student(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeString(this.email);
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
