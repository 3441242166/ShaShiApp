package com.shashiwang.shashiapp.util;

import android.content.Context;


import com.example.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    public static Map<String,Integer> getJsonFormAssets(Context context, String name){
        StringBuilder builder = new StringBuilder();
        InputStream inputStream ;
        try {
            inputStream = context.getResources().getAssets().open(name);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                builder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type amountCurrencyType = new TypeToken<HashMap<String, Integer>>(){}.getType();

        return gson.fromJson(builder.toString(), amountCurrencyType);
    }

}
