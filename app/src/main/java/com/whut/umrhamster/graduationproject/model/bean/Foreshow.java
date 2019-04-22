package com.whut.umrhamster.graduationproject.model.bean;

public class Foreshow {
    private String cover;
    private int icon;
    private String title;
    private String nickname;
    public Foreshow(){}
    public Foreshow(String cover, int icon, String nickname, String title){
        this.cover = cover;
        this.icon = icon;
        this.title = title;
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
