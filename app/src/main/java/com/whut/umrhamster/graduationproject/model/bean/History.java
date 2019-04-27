package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

public class History implements Parcelable {
    private int id;
    private int contentId;
    private int studentId;
    private int type;
    private int watchedTime;  //shipin
    private Date lastTime;


    private String title;
    private String brief;  //shipin
    private String cover;
    private String path;
    private int owner;  // /uploader
    private String nickname;
    private int totalTime; //shipin
    private String levelname;
    private int status; //live

    public History(String title, int totalTime){
        this.title = title;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWatchedTime() {
        return watchedTime;
    }

    public void setWatchedTime(int watchedTime) {
        this.watchedTime = watchedTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
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
        dest.writeInt(this.contentId);
        dest.writeInt(this.studentId);
        dest.writeInt(this.type);
        dest.writeInt(this.watchedTime);
        dest.writeSerializable(this.lastTime);
        dest.writeString(this.title);
        dest.writeString(this.brief);
        dest.writeString(this.cover);
        dest.writeString(this.path);
        dest.writeInt(this.owner);
        dest.writeString(this.nickname);
        dest.writeInt(this.totalTime);
        dest.writeString(this.levelname);
        dest.writeInt(this.status);
    }

    public History() {
    }

    protected History(Parcel in) {
        this.id = in.readInt();
        this.contentId = in.readInt();
        this.studentId = in.readInt();
        this.type = in.readInt();
        this.watchedTime = in.readInt();
        this.lastTime = (Timestamp) in.readSerializable();
        this.title = in.readString();
        this.brief = in.readString();
        this.cover = in.readString();
        this.path = in.readString();
        this.owner = in.readInt();
        this.nickname = in.readString();
        this.totalTime = in.readInt();
        this.levelname = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
