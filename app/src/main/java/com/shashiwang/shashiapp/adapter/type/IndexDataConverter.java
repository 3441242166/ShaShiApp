package com.shashiwang.shashiapp.adapter.type;

import com.example.ui.recycler.DataConverter;
import com.example.ui.recycler.MultipleItemEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndexDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        // 获取json的Data
        final JSONArray dataArray = null;
        final int size = dataArray.length();
        for(int x=0;x<size;x++){
            //final JSONObject data = dataArray.getJSONObject(x);

        }
        MultipleItemEntity entity = MultipleItemEntity.builder()
                .build();
        ENTITIES.add(entity);
        return ENTITIES;
    }

}
