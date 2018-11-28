package com.example.util.timer;

import java.util.TimerTask;

public class BaseTimerTask extends TimerTask {

    private ITimerListener iTimerListener;

    private BaseTimerTask(ITimerListener listener){
        this.iTimerListener = listener;
    }

    @Override
    public void run() {
        if(iTimerListener !=null){
            iTimerListener.onTimer();
        }
    }
}
