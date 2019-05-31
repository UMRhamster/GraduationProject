package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

public class History implements Parcelable {
//    private int id;
//    private int contentId;
//    private int studentId;
//    private int type;
//    private int watchedTime;  //shipin
//    private Date lastTime;
//
//
//    private String title;
//    private String brief;  //shipin
//    private String cover;
//    private String path;
//    private int owner;  // /uploader
//    private String nickname;
//    private int totalTime; //shipin
//    private String levelname;
//    private int status; //live

    private int id;
    private int contentId;
    private Student student;
    private int type;
    private int watchedTime;
    private Date lastTime;
    private Live live;
    private Video video;
    //时间标志位
    private String tag;

    public History(String tag){
        this.tag = tag;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.contentId);
        dest.writeParcelable(this.student, flags);
        dest.writeInt(this.type);
        dest.writeInt(this.watchedTime);
        dest.writeLong(this.lastTime != null ? this.lastTime.getTime() : -1);
        dest.writeParcelable(this.live, flags);
        dest.writeParcelable(this.video, flags);
        dest.writeString(this.tag);
    }

    public History() {
    }

    protected History(Parcel in) {
        this.id = in.readInt();
        this.contentId = in.readInt();
        this.student = in.readParcelable(Student.class.getClassLoader());
        this.type = in.readInt();
        this.watchedTime = in.readInt();
        long tmpLastTime = in.readLong();
        this.lastTime = tmpLastTime == -1 ? null : new Date(tmpLastTime);
        this.live = in.readParcelable(Live.class.getClassLoader());
        this.video = in.readParcelable(Video.class.getClassLoader());
        this.tag = in.readString();
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
