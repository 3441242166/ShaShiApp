package com.shashiwang.shashiapp.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateUtil {
    private static final String TAG = "DateUtil";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";

    public static String getNow(String type) {
        Date currentTime = new Date();
        return new SimpleDateFormat(type).format(currentTime);
    }

    public static String getStringByDate(Date date,String type){
        return new SimpleDateFormat(type).format(date);
    }

    public static String getTimeString(String dateTime) {
        String[] ar = dateTime.split(" ");
        if(ar.length<2){
            Log.i("date","error dateTime");
            return "";
        }
        Log.i("date","getNowTimeString    "+ar[1]);
        return ar[1];
    }

    /**
     * 规则:
     * 大于6小时 显示详细日期时间
     * 大于60分钟 显示 n 小时前
     * 显示 n 分钟前
     * @param startTime 开始时间
     * @return
     */
    public static String getDifferentString(String startTime){
        Log.i(TAG, "getDifferentString: startTime = "+ startTime + "  nowTime = "+getNow(DATE_TIME));
        String now = getNow(DATE_TIME);

        DateFormat sdf = new SimpleDateFormat(DATE_TIME);

        try {
            Date d1 = sdf.parse(now);
            Date d2 = sdf.parse(startTime);

            long diff = (d1.getTime() - d2.getTime()) /1000;

            int difMinutes = (int) (diff / 60);
            Log.i(TAG, "getDifferentString: difMinutes = "+difMinutes);
            if(difMinutes < 60){
                if(difMinutes < 3){
                    Log.i(TAG, "getDifferentString: 刚刚");
                    return "刚刚";
                }
                Log.i(TAG, "getDifferentString: " + difMinutes + "分钟前");
                return difMinutes + "分钟前";
            }

            int difHours = difMinutes / 60;
            Log.i(TAG, "getDifferentString: difHours = "+difHours);
            if(difHours < 24){
                Log.i(TAG, "getDifferentString: "+ difHours + "小时前");
                return difHours + "小时前";
            }
            int difDay = difMinutes / 60 / 24;
            Log.i(TAG, "getDifferentString: difDay ="+difDay);
            return difDay + "天前";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}

