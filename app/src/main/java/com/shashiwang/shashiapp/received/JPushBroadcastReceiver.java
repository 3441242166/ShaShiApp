package com.shashiwang.shashiapp.received;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

public class JPushBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushBroadcastReceiver";

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);

        Log.i(TAG, "onReceive: title = "+title);
        Log.i(TAG, "onReceive: message = "+message);
        Log.i(TAG, "onReceive: extras = "+extras);
        Log.i(TAG, "onReceive: file = "+file);

        //语音播放Value

//        Intent service = new Intent(context,TestService.class);
//        context.startService(service);

        test(context,"");
    }

    private void test(Context context, String str){
        LoggerProxy.printable(true);
        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setAppId("15221121");
        mSpeechSynthesizer.setApiKey("vTLeIRab50P12ZP71vlK6GZp","dPnoKK8jM7lgR1I3wf7K6ljrqn503guI");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                Log.i(TAG, "onSynthesizeStart: ");
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                Log.i(TAG, "onSynthesizeDataArrived: ");
            }

            @Override
            public void onSynthesizeFinish(String s) {
                Log.i(TAG, "onSynthesizeFinish: ");
            }

            @Override
            public void onSpeechStart(String s) {
                Log.i(TAG, "onSpeechStart: ");
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                Log.i(TAG, "onSpeechProgressChanged: ");
            }

            @Override
            public void onSpeechFinish(String s) {
                Log.i(TAG, "onSpeechFinish: ");
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.i(TAG, "onError: "+ s +"\n"+speechError);
            }
        });

        mSpeechSynthesizer.speak(str);
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName))
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知 ==" + runningService.get(i).service.getClassName().toString());
            if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

}
