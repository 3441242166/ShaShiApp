package com.example.ui.recycler;

import java.util.LinkedHashMap;

public class MultipleItemEntityBuilder {

    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    public MultipleItemEntityBuilder(){
        FIELDS.clear();
    }

    public final MultipleItemEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleItemEntityBuilder setField(Object key, Object value){
        FIELDS.put(key,value);
        return this;
    }

    public final MultipleItemEntityBuilder setItemType(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }

}
