package com.example.config;

import android.content.Context;

public final class Config {

    public static Configurator init(Context context){
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static <T> T getConfigurations(Object key){
        return (T) Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApplication() {
        return (Context) Configurator.getInstance().getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }
}
