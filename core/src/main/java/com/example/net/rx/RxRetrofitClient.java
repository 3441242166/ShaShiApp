package com.example.net.rx;

import com.example.net.HttpMethod;
import com.example.net.RetrofitCreators;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRetrofitClient {

    private final String URL;
    private final Map<String,Object> PARAMS;
    private final RequestBody BODY;
    private final File FILE;

    RxRetrofitClient(String url, Map<String, Object> params,
                     RequestBody body,
                     File file) {
        URL = url;
        PARAMS = params;
        BODY = body;
        FILE = file;
    }

    public static RxRetrofitClientBuilder builder(){
        return new RxRetrofitClientBuilder();
    }

    private Observable<String> request(HttpMethod method){
        final RxRetrofitService service = RetrofitCreators.getRxRetrofitService();

        Observable<String> observable = null;

        switch (method){
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
               final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
               final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable = RetrofitCreators.getRxRetrofitService().upload(URL, body);
               break;
        }

        return observable;
    }

    //----------------------------------------------------------------------------------------------

    public final Observable<String>  get(){
        return request((HttpMethod.GET));
    }

    public final Observable<String>  post(){
        if(BODY == null){
            return request((HttpMethod.POST));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String>  delete(){
        return request((HttpMethod.DELETE));
    }

    public final Observable<String>  put(){
        if(BODY == null){
            return request((HttpMethod.PUT));
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String>  upload(){
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody>  download(){
        final Observable<ResponseBody> responseBodyObservable = RetrofitCreators.getRxRetrofitService().download(URL,PARAMS);
        return responseBodyObservable;
    }

    //----------------------------------------------------------------------------------------------

}
