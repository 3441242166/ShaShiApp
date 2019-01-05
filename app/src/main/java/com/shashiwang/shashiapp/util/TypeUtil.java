package com.shashiwang.shashiapp.util;

import com.example.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeUtil {

    private static Map<String,Integer> carType;
    private static Map<String,Integer> yearType;

    static {
        carType = FileUtil.getJsonFormAssets(Config.getApplication(),"carType.json");
        yearType = FileUtil.getJsonFormAssets(Config.getApplication(),"workYear.json");
    }

    public static int getCarInt(String str){
        if(carType!=null){
            return carType.get(str);
        }else {
            return -1;
        }
    }

    public static String getCarString(int val){
        if(carType!=null){
            for(String key:carType.keySet()){
                if(carType.get(key) == val){
                    return key;
                }
            }
        }
        return "";
    }

    public static List<String> getCarList(){
        List<String> list = new ArrayList<>();
        for( String key :carType.keySet()){
            list.add(key);
        }
        return list;
    }

    //----------------------------------------------------------------------------------------------

    public static int getYearInt(String str){
        if(yearType!=null){
            return yearType.get(str);
        }else {
            return -1;
        }
    }

    public static String getYearString(int val){
        if(yearType!=null){
            for(String key:yearType.keySet()){
                if(yearType.get(key) == val){
                    return key;
                }
            }
        }
        return "";
    }

    public static List<String> getYearList(){
        List<String> list = new ArrayList<>();
        for( String key :yearType.keySet()){
            list.add(key);
        }
        return list;
    }
}
