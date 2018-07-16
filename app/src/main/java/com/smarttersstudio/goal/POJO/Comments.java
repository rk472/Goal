package com.smarttersstudio.goal.POJO;

public class Comments {
    String uid;

    public Comments() {
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comments(String uid, String text, String time) {

        this.uid = uid;
        this.text = text;
        this.time = time;
    }

    String text;
    String time;

}
