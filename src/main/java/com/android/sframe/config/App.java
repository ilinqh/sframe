package com.android.sframe.config;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by lin on 2017/9/7.
 */

public class App extends Application {

    public static App app;

    // OkHttpClient
    public static OkHttpClient okHttpClient;

    // 上下文
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    public static App getApp() {
        if (app == null) {
            synchronized (App.class) {
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }

    public static Context getContext() {
        return context;
    }

    public static OkHttpClient getOkHttpClient() {
        if (null == okHttpClient) {
            synchronized (OkHttpClient.class) {
                if (null == okHttpClient) {
                    // 设置 OkHttp 缓存
                    File baseDir = context.getCacheDir();
                    Cache cache = null;
                    if (null != baseDir) {
                        File cacheDir = new File(baseDir, "HttpResponseCache");
                        cache = new Cache(cacheDir, 10 * 10 * 1024);
                    }
                    // init okHttpClient
                    okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).cache(cache).build();
                }
            }
        }
        return okHttpClient;
    }

}
