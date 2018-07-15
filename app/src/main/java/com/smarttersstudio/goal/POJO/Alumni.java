package com.smarttersstudio.goal.POJO;

public class Alumni {
    String uid,name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Alumni() {

    }

    public String getName() {

        return name;
    }

    public Alumni(String uid, String name) {

        this.uid = uid;
        this.name = name;
    }
}
