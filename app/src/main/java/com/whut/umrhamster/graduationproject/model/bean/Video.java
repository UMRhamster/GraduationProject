package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Video implements Parcelable {
//    //记录
//    private int id;
//    private String title;
//    private Teacher author;
//    private Calendar date;
//    private int totalTime;
//    private int watchedTime;
//    private String cover;
//    private String path;
//    //播放
//    private int nofw;
//    private int supportNum;
//    private int CommentNum;
//    private int unSupportNum;

    private int id;
    private String title;
    private String brief;
    private String path;
    private String cover;
    private Teacher uploader;      //上传者
    private Date uploadtime;
    private Classification classify;  //分类
    private int totaltime;
    private int viewers;
    private int supportnum;


    public Video(){}

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

    public Teacher getUploader() {
        return uploader;
    }

    public void setUploader(Teacher uploader) {
        this.uploader = uploader;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Classification getClassify() {
        return classify;
    }

    public void setClassify(Classification classify) {
        this.classify = classify;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public int getSupportnum() {
        return supportnum;
    }

    public void setSupportnum(int supportnum) {
        this.supportnum = supportnum;
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
        dest.writeParcelable(this.uploader, flags);
        dest.writeLong(this.uploadtime != null ? this.uploadtime.getTime() : -1);
        dest.writeParcelable(this.classify, flags);
        dest.writeInt(this.totaltime);
        dest.writeInt(this.viewers);
        dest.writeInt(this.supportnum);
    }

    protected Video(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.brief = in.readString();
        this.path = in.readString();
        this.cover = in.readString();
        this.uploader = in.readParcelable(Teacher.class.getClassLoader());
        long tmpUploadtime = in.readLong();
        this.uploadtime = tmpUploadtime == -1 ? null : new Date(tmpUploadtime);
        this.classify = in.readParcelable(Classification.class.getClassLoader());
        this.totaltime = in.readInt();
        this.viewers = in.readInt();
        this.supportnum = in.readInt();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
