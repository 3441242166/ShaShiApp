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
import com.example.config.Config;
import com.shashiwang.shashiapp.base.BaseApplication;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

public class JPushBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushBroadcastReceiver";

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.i(TAG, "onReceive: content = "+content);

        String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        String xxx = JPushInterface.getRegistrationID(context);
        Log.d(TAG, "[MyReceiver] 接收Registration Id : " + xxx);

        test(content);
    }

    private void test(String str){

        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_PCM);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_PCM);
        mSpeechSynthesizer.setContext(Config.getApplication());
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

        //mSpeechSynthesizer.setAppId("15221121");
        mSpeechSynthesizer.setApiKey("vTLeIRab50P12ZP71vlK6GZp",
                "dPnoKK8jM7lgR1I3wf7K6ljrqn503guI");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER ,"3");
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

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

}
