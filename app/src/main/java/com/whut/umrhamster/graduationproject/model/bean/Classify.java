package com.whut.umrhamster.graduationproject.model.bean;

public class Classify {
    private String title;
    private int icon;

    public Classify(){}

    public Classify(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
