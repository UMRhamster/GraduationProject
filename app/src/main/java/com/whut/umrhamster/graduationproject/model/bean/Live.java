package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Live implements Parcelable {
    private int id;
    private String title;
    private String brief;
    private String path;
    private String cover;
    private Teacher teacher;
    private Classification classify;
    private int viewers;
    private int status;
//    private Timestamp start_time;

    public Live(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Classification getClassify() {
        return classify;
    }

    public void setClassify(Classification classify) {
        this.classify = classify;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.brief);
        dest.writeString(this.path);
        dest.writeString(this.cover);
        dest.writeParcelable(this.teacher, flags);
        dest.writeParcelable(this.classify, flags);
        dest.writeInt(this.viewers);
        dest.writeInt(this.status);
    }

    protected Live(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.brief = in.readString();
        this.path = in.readString();
        this.cover = in.readString();
        this.teacher = in.readParcelable(Teacher.class.getClassLoader());
        this.classify = in.readParcelable(Classification.class.getClassLoader());
        this.viewers = in.readInt();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<Live> CREATOR = new Parcelable.Creator<Live>() {
        @Override
        public Live createFromParcel(Parcel source) {
            return new Live(source);
        }

        @Override
        public Live[] newArray(int size) {
            return new Live[size];
        }
    };
}
