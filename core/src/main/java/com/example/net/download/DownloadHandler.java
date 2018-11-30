package com.example.net.download;

import android.os.AsyncTask;
import android.util.Log;

import com.example.net.RetrofitCreators;
import com.example.net.callback.IError;
import com.example.net.callback.IFailure;
import com.example.net.callback.IRequest;
import com.example.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadHandler {
    private static final String TAG = "DownloadHandler";

    private final String URL;
    private final Map<String,Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public DownloadHandler(String url,
                           Map<String, Object> params,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IError error,
                           IFailure failure) {
        URL = url;
        PARAMS = params;
        REQUEST = request;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        ERROR = error;
        FAILURE = failure;
    }

    public final void handleDownload(){
        if(REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RetrofitCreators.getRetrofitService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            final ResponseBody body = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, body, NAME);

                            //这里一定注意判断的 负责文件下载不完整
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestFinish();
                                }
                            }
                        }else {
                            if(ERROR!=null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "onFailure: "+t);
                        if(FAILURE!=null){
                            FAILURE.onFailure(t);
                        }
                    }
                });

    }

}
