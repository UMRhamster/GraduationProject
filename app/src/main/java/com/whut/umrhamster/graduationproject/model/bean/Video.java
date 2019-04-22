package com.whut.umrhamster.graduationproject.model.bean;

import java.util.Calendar;

public class Video {
    //记录
    private String title;
    private Teacher author;
    private Calendar date;
    private int totalTime;
    private int watchedTime;
    //播放
    private String url;
    private int supportNum;
    private int CommentNum;
    private int unSupportNum;
    public Video(){}

    public Video(String title, int totalTime){
        this.title = title;
        this.totalTime = totalTime;
    }

    public Video(String title, Teacher author, Calendar date, int totalTime, int watchedTime){
        this.title = title;
        this.author = author;
        this.date = date;
        this.totalTime = totalTime;
        this.watchedTime = watchedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teacher getAuthor() {
        return author;
    }

    public void setAuthor(Teacher author) {
        this.author = author;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getWatchedTime() {
        return watchedTime;
    }

    public void setWatchedTime(int watchedTime) {
        this.watchedTime = watchedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(int supportNum) {
        this.supportNum = supportNum;
    }

    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }

    public int getUnSupportNum() {
        return unSupportNum;
    }

    public void setUnSupportNum(int unSupportNum) {
        this.unSupportNum = unSupportNum;
    }
}
