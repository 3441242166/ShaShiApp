package com.shashiwang.shashiapp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wanhao on 2018/4/8.
 */

public class Homework implements Serializable {

    @SerializedName("course_title_name")
    String title;

    public Homework(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
