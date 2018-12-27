package com.shashiwang.shashiapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Map;

public class MessageBean implements MultiItemEntity {

    private int type;
    private BaseMessage bean;

    public MessageBean(int type, BaseMessage bean){
        this.type = type;
        this.bean = bean;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public BaseMessage getBean() {
        return bean;
    }

    public void setBean(BaseMessage bean) {
        this.bean = bean;
    }
}
