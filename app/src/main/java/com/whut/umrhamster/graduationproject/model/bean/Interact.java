package com.whut.umrhamster.graduationproject.model.bean;

public class Interact {
    private String nickname;
    private String content;

    public Interact(){}

    public Interact(String nickname, String content){
        this.nickname = nickname;
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
