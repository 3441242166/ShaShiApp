package com.example.config;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {

    private static final HashMap<Object,Object> CONFIGS = new HashMap<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    public final void configure(){
        CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    public HashMap<Object,Object> getConfigs(){
        return CONFIGS;
    }

    public Object getConfigs(String key){
        return CONFIGS.get(key);
    }

    //----------------------------------------------------------------------------------------------

    public final Configurator withApiHost(String host){
        CONFIGS.put(ConfigType.API_HOST,host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors( ArrayList<Interceptor> interceptors)                         {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withApiHost(String key,Object val){
        CONFIGS.put(key,val);
        return this;
    }

    //----------------------------------------------------------------------------------------------

    private void checkConfiguration(){
        final boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("config now ready");
        }
    }

    public final <T> T getConfiguration(Object key){
        checkConfiguration();
        return (T)CONFIGS.get(key);
    }

}
