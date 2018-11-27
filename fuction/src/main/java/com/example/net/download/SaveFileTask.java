package com.example.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.net.callback.IRequest;
import com.example.net.callback.ISuccess;
import com.example.config.Config;
import com.example.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object,Void,File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... objects) {

        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[3];
        final InputStream is = body.byteStream();
        if(TextUtils.isEmpty(downloadDir)){
            downloadDir = "down_loads";
        }
        if(TextUtils.isEmpty(extension)){
            extension = "";
        }
//        if(name == null){
//            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
//        }else {
//            return FileUtil.writeToDisk(is,downloadDir,name);
//        }
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestFinish();
        }

        autoInstallApk(file);
    }


    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getName()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Config.getApplication().startActivity(install);
        }
    }

}
