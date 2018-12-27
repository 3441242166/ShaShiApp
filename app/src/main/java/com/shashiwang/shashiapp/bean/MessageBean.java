package com.shashiwang.shashiapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Map;

public class MessageBean<T> implements MultiItemEntity {

    private int type;
    private T bean;

    public MessageBean(int type, T bean){
        this.type = type;
        this.bean = bean;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }
}
