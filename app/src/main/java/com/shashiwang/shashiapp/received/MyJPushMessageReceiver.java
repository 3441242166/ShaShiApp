package com.shashiwang.shashiapp.received;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "MyJPushMessageReceiver";

    @Override
    public void onTagOperatorResult(Context context,JPushMessage jPushMessage) {
        Log.i(TAG, "onTagOperatorResult: "+jPushMessage.toString());
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        Log.i(TAG, "onCheckTagOperatorResult: "+jPushMessage.toString());
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.i(TAG, "onAliasOperatorResult: "+jPushMessage.toString());
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.i(TAG, "onMobileNumberOperatorResult: "+jPushMessage.toString());
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}
