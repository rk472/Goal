package com.smarttersstudio.goal.POJO;

public class Post {
    private String name,tag,text,time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Post() {

    }

    public Post(String name, String tag, String text, String time) {

        this.name = name;
        this.tag = tag;
        this.text = text;
        this.time = time;
    }
}
