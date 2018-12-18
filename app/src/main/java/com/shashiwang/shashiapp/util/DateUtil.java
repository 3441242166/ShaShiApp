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

    /*    date_1 大 返回 1 --- date_1 小 返回 -1 ---else 返回 0 */
    public static int compareStringByDate(String date_1, String date_2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date_1);
            Date dt2 = df.parse(date_2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int differentDays(String bdate, String smdate){

        DateFormat  sdf=new SimpleDateFormat(DATE_TIME);

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            Log.i(TAG, "differentDays: " + days);
            return (int)days + 1;
        }catch (Exception e){
            Log.i(TAG, "differentHours: "+e);
            return -1;
        }

    }

    public static int differentHours(String bdate, String smdate){

        DateFormat  sdf=new SimpleDateFormat(DATE_TIME);

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            diff  = diff - days * (1000 * 60 * 60 * 24);
            long hours = diff / (1000 * 60 * 60 );
            Log.i(TAG, "differentHours: " + hours);
            return (int)hours+1;
        }catch (Exception e){
            Log.i(TAG, "differentHours: "+e);
            return -1;
        }

    }

    public static int differentMinutes(String bdate, String smdate){

        DateFormat sdf = new SimpleDateFormat(DATE_TIME);

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            diff  = diff - days * (1000 * 60 * 60 * 24);
            long hours = diff / (1000 * 60 * 60 );
            diff  = diff - hours * (1000 * 60 * 60);
            long minute = diff / (1000 * 60 );
            Log.i(TAG, "differentMinutes: " + minute);
            return (int)minute+1;
        }catch (Exception e){
            Log.i(TAG, "differentHours: "+e);
            return -1;
        }
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
            if(difHours < 6){
                Log.i(TAG, "getDifferentString: "+ difHours + "小时前");
                return difHours + "小时前";
            }
            Log.i(TAG, "getDifferentString: "+startTime);
            return startTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}

