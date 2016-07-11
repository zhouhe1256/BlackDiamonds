package com.ljs.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.bjcathay.android.async.LooperCallbackExecutor;
import com.bjcathay.android.cache.ChainedCache;
import com.bjcathay.android.cache.DiskCache;
import com.bjcathay.android.cache.MemoryCache;
import com.bjcathay.android.remote.Http;
import com.bjcathay.android.remote.HttpOption;
import com.bjcathay.android.remote.IContentDecoder;
import com.ljs.constant.ApiUrl;
import com.ljs.model.LoginArrayModel;
import com.ljs.util.PreferencesUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zhouhe on 2016/7/6.
 */
public class HApplication extends Application {
    private static HApplication hApplication;
    private static volatile boolean httpInited;
    private static File baseDir;
    @Override
    public void onCreate() {
        super.onCreate();
        hApplication = this;
        initHttp(this);
    }
    public static HApplication getInstance(){
        return hApplication;
    }

    public synchronized void initHttp(Context context) {
        if (httpInited) {
            return;
        }

        httpInited = true;

        baseDir = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                Environment.getExternalStorageDirectory() : context.getCacheDir();
        baseDir = new File(baseDir, "ljs");
        DiskCache.setBaseDir(baseDir);
        String token = getApiToken();
       // String token="2342343243";
        DiskCache<String, byte[]> apiCache = new DiskCache<String, byte[]>("api", new DiskCache.ByteArraySerialization());
        Http.instance().option(HttpOption.BASE_URL, ApiUrl.HOST_URL).
                option(HttpOption.MIME, "application/json").
                param("token", token).
                option(HttpOption.CONNECT_TIMEOUT, 10000).
                option(HttpOption.READ_TIMEOUT, 10000).
                option(HttpOption.X_Token, token).
                option(HttpOption.X_Version, ApiUrl.VERSION).
                option(HttpOption.X_OS, ApiUrl.OS).
                setContentDecoder(new IContentDecoder.JSONDecoder()).
                cache(apiCache).fallbackToCache(true).
                always(new ICallback() {
                    @Override
                    public void call(Arguments arguments) {
                        Object object = arguments.get(0);

                        if (object instanceof Throwable) {
                            Throwable t = (Throwable) object;
                            StringWriter writer = new StringWriter();
                            writer.write(t.getMessage() + "\n");
                            t.printStackTrace(new PrintWriter(writer));
                            String s = writer.toString();
                            System.out.println(s);
                            return;
                        }

                        if (!(object instanceof JSONObject)) {
                            return;
                        }
                        JSONObject json = arguments.get(0);
                        if (!json.optBoolean("success")) {
                            String errorMessage = json.optString("message");
                            int code = json.optInt("code");
                            if (code == 13005) {
                            } else {
                            }
                        }
                    }
                }).start(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        }).complete(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        }).
                callbackExecutor(new LooperCallbackExecutor()).
                fail(new ICallback() {
                    @Override
                    public void call(Arguments arguments) {
                    }
                });
        ChainedCache<String, byte[]> imageCache = ChainedCache.create(
                800 * 1024 * 1024, new MemoryCache.ByteArraySizer<String>(),
                "images", new DiskCache.ByteArraySerialization<String>()
        );
        Http.imageInstance().cache(imageCache).baseUrl(ApiUrl.HOST_URL).
                aheadReadInCache(true);
    }

    public void updateApiToken() {
        String token = PreferencesUtils.getString(hApplication, "token");
        Http.instance().param("token", token).option(HttpOption.X_Token, token);
    }

    public static File getBaseDir() {
        return baseDir;
    }

    public static boolean isLogin(){
        String token = PreferencesUtils.getString(hApplication, "token");
//        Log.i("tosss",token);
        if(token==null||token.equals("")){
            return false;
        }else{
            return true;
        }
    }

    private String getApiToken() {
        return PreferencesUtils.getString(this,"token");
    }



}
