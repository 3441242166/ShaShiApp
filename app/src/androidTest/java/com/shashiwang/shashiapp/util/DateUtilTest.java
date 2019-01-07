package com.shashiwang.shashiapp.util;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class DateUtilTest {
    private static final String TAG = "DateUtilTest";

    @Test
    public void getDifferentString() {

        Log.i(TAG, "getDifferentString: aaaaaaaa");

        boolean result = "18210741899".matches("\\d{11}");
        Log.i("tag", "#####:" + result);
        /**
         * 验证邮箱
         */
        assertEquals("result:", result, true);
    }
}