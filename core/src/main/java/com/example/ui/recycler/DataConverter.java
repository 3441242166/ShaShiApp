package com.example.ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String jsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.jsonData = json;
        return this;
    }

    public String getJsonData( ){
        if(TextUtils.isEmpty(jsonData)){
            throw new NullPointerException("json is null");
        }
        return jsonData;
    }

}
