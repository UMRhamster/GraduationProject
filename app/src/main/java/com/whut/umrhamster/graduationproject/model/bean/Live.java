package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Live implements Parcelable {
    private int id;
    private String title;
    private String path;
    private String cover;
    private int owner;
    private String ownername;
    private String avatar;
    private String levelname;
    private Calendar calendar;
    private int viewers;

    private int status;  //0未开播，//1正在播

    public Live(){}

    public Live(String title, Calendar calendar, String cover){
        this.title = title;
        this.calendar = calendar;
        this.cover = cover;
    }


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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
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
        dest.writeString(this.path);
        dest.writeString(this.cover);
        dest.writeInt(this.owner);
        dest.writeString(this.ownername);
        dest.writeString(this.avatar);
        dest.writeString(this.levelname);
        dest.writeSerializable(this.calendar);
        dest.writeInt(this.viewers);
        dest.writeInt(this.status);
    }

    protected Live(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.path = in.readString();
        this.cover = in.readString();
        this.owner = in.readInt();
        this.ownername = in.readString();
        this.avatar = in.readString();
        this.levelname = in.readString();
        this.calendar = (Calendar) in.readSerializable();
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
