package com.jinandaxue.utils;


import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.jinandaxue.entity.UserBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liucong on 2019/4/5.
 */

public class MyApplication extends Application {
    public static  UserBean userBean;
    private static Set<AppCompatActivity> recordsActivity=new HashSet<>();

    public static  void addRecordActivity(AppCompatActivity activity){
        recordsActivity.add(activity);
    }
    public static  void clearRecordActivity(){
        for (AppCompatActivity activity:recordsActivity) {
            activity.finish();
        }
    }
    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
