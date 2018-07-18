package com.smarttersstudio.goal.POJO;

public class PDF {
    String url;

    public PDF(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    String desc;

    public PDF() {
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
