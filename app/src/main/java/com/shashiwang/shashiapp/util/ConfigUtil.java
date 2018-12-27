package com.shashiwang.shashiapp.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.config.Config;
import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.constant.MessageType;

import static com.shashiwang.shashiapp.constant.MessageType.*;

public class ConfigUtil {
    private static final String TAG = "ConfigUtil";

    public static void configJPush(String str,int isVoice){

        String[] config = str.split(",");

        SharedPreferencesHelper.put(KEY_FACTORY,false);
        SharedPreferencesHelper.put(KEY_STATION,false);
        SharedPreferencesHelper.put(KEY_FREIGHT,false);
        SharedPreferencesHelper.put(KEY_DRIVER,false);
        SharedPreferencesHelper.put(KEY_CAR,false);

        if(isVoice == 0){
            SharedPreferencesHelper.put(KEY_VOICE,false);
        }else {
            SharedPreferencesHelper.put(KEY_VOICE,true);
        }

        for(String s : config){
            int temp = Integer.valueOf(s);
            Log.i(TAG, "configJPush: temp = " + temp);
            switch (temp){
                case MessageType.FACTORY:
                    SharedPreferencesHelper.put(KEY_FACTORY,true);
                    break;
                case MessageType.STATION:
                    SharedPreferencesHelper.put(KEY_STATION,true);
                    break;
                case MessageType.CAR:
                    SharedPreferencesHelper.put(KEY_CAR,true);
                    break;
                case MessageType.FREIGHT:
                    SharedPreferencesHelper.put(KEY_FREIGHT,true);
                    break;
                case MessageType.DRIVER:
                    SharedPreferencesHelper.put(KEY_DRIVER,true);
                    break;
            }
        }

    }

}
