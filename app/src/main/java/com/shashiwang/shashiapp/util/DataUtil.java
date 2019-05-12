package com.shashiwang.shashiapp.util;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.constant.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {

    public static Map<String, Count> countMap = new HashMap<>();
    public static Count nowCount;
    public static int type = 0;

    public static void init(){
        String json = (String) SharedPreferencesHelper.getSharedPreference("counts",null);
        Log.i("DataUtil", "login: " + json);
        if(TextUtils.isEmpty(json)){
            countMap  = new HashMap<>();
        }else {
            countMap = new Gson().fromJson(json,new TypeToken<HashMap<String,Count>>(){}.getType());
        }
        String name = (String) SharedPreferencesHelper.getSharedPreference(Constant.USER_NAME,"");
        nowCount = countMap.get(name);
    }

    public static void save(){
        SharedPreferencesHelper.put("counts",new Gson().toJson(countMap));
    }

    public static boolean hasContains(String count){
        return countMap.containsKey(count);
    }

    public static void createCount(Count count) {
        countMap.put(count.count,count);
        save();
    }

    public static void setNewLocation(BDLocation location) {
        nowCount.saveLoaction(location.getLatitude(),location.getLongitude());
        save();
    }

    public static List<Count> getTargetData() {
        List<Count> list = new ArrayList<>(nowCount.data.size());
        for(String str:nowCount.data){
            list.add(countMap.get(str));
        }
        return list;
    }

    public static void addCount(String toString) {
        countMap.get(toString).data.add(nowCount.count);
        save();
    }

    public static List<Count> getMyWatcher() {
        List<Count> list = new ArrayList<>(nowCount.data.size());
        for(Count count:countMap.values()){
            for(String str:count.data){
                if(str.equals(nowCount.count)){
                    list.add(count);
                }
            }
        }
        return list;
    }
}
