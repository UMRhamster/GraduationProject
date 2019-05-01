package com.whut.umrhamster.graduationproject.model.bean;

public class InfoChildBean {
    private int id;
    private String name;
    private int time;

    public InfoChildBean(){}
    public InfoChildBean(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
