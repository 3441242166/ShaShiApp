package com.shashiwang.shashiapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Map;

public class MessageBean<T> implements MultiItemEntity {

    private int type;

    public MessageBean(int type){
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
