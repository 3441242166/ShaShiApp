package com.shashiwang.shashiapp.received;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.config.Config;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.activity.message.DriverMessageActivity;
import com.shashiwang.shashiapp.activity.message.FactoryMessageActivity;
import com.shashiwang.shashiapp.activity.message.FreightMessageActivity;
import com.shashiwang.shashiapp.activity.message.StationMessageActivity;
import com.shashiwang.shashiapp.base.BaseApplication;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.provider.Contacts.SettingsColumns.KEY;
import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.constant.MessageType.CAR;
import static com.shashiwang.shashiapp.constant.MessageType.DRIVER;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;
import static com.shashiwang.shashiapp.constant.MessageType.FREIGHT;
import static com.shashiwang.shashiapp.constant.MessageType.KEY_VOICE;
import static com.shashiwang.shashiapp.constant.MessageType.STATION;

public class JPushBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushBroadcastReceiver";

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        String jsonData = bundle.getString(JPushInterface.EXTRA_EXTRA);
        JPushBean bean = new Gson().fromJson(jsonData,new TypeToken<JPushBean>(){}.getType());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
            Intent i = null;

            switch (bean.category_id){
                case FACTORY:
                    i = new Intent(context, FactoryMessageActivity.class);
                    break;
                case STATION:
                    i = new Intent(context, StationMessageActivity.class);
                    break;
                case FREIGHT:
                    i = new Intent(context, FreightMessageActivity.class);
                    break;
                case CAR:
                    i = new Intent(context, CarMessageActivity.class);
                    break;
                case DRIVER:
                    i = new Intent(context, DriverMessageActivity.class);
                    break;
            }
            i.putExtra(ID,bean.id);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }



        // 语音内容
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.i(TAG, "onReceive: content = "+content);

        boolean isVoice = (boolean) SharedPreferencesHelper.getSharedPreference(KEY_VOICE,false);
        if(isVoice ){
            test(content);
        }
    }

    private void test(String str){
        Log.i(TAG, "test: ");

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
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
                JPushBean bean = new Gson().fromJson(data,new TypeToken<JPushBean>(){}.getType());
//                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//                    Log.i(TAG, "This message has no Extra data");
//                    continue;
//                }
//
//                try {
//                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                    Log.i(TAG, "printBundle: json = " + json);
//                    Iterator<String> it =  json.keys();
//
//                    while (it.hasNext()) {
//                        String myKey = it.next();
//                        sb.append("\nkey:" + key + ", value: [" +
//                                myKey + " - " +json.optString(myKey) + "]");
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "Get message extra JSON error!");
//                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    static class JPushBean{
        int category_id;
        int id;
    }

}
