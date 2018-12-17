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
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        String dateString = formatter.format(currentTime);
        Log.i("date","getNowDateTimeString    "+dateString);
        return dateString;
    }

    public static String getStringByDate(Date date,String type){
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Log.i(TAG, "getStringByDateTime: dateString " +formatter.format(date));
        return formatter.format(date);
    }

    public static String getTimeString(String dateTime,String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date date= new Date();
        try {
            date = formatter.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = formatter.format(date);
        Log.i("date","getNowTimeString    "+dateString);
        return dateString;
    }

    public static Date getTimeByDateString(String date){
        Date mDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
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

        DateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return (int)days;
        }catch (Exception e){
            return -1;
        }

    }

    public static int differentHours(String bdate, String smdate){

        DateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return (int)days;
        }catch (Exception e){
            return -1;
        }

    }

    public static int differentMinutes(String bdate, String smdate){

        DateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = sdf.parse(bdate);
            Date d2 = sdf.parse(smdate);

            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return (int)days;
        }catch (Exception e){
            return -1;
        }

    }
}

