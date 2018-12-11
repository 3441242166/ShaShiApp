package com.shashiwang.shashiapp.bean;

public class BannerBean {
    private String imgUrl;
    private String title;
    private int imgId;

    public String getImgUrl() {
        return imgUrl;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgId = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
