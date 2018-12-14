package com.shashiwang.shashiapp.util;

import android.util.Log;

import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class LatLngListener implements CloudListener {
    private static final String TAG = "LatLngListener";

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {

    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult cloudRgcResult, int i) {
        Log.i(TAG, "onGetCloudRgcResult: " + cloudRgcResult.customLocationDescription);
        Log.i(TAG, "onGetCloudRgcResult: " + cloudRgcResult.formattedAddress);
        Log.i(TAG, "onGetCloudRgcResult: " + cloudRgcResult.message);
        Log.i(TAG, "onGetCloudRgcResult: " + cloudRgcResult.recommendedLocationDescription);

        EventBus.getDefault().post(cloudRgcResult);
    }
}
