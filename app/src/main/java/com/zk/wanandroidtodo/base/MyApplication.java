package com.zk.wanandroidtodo.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.SpUtils;

/**
 * @description: app初始化
 * @author: zhukai
 * @date: 2018/8/14 14:49
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        application = this;
        initNightMode(); // 初始化夜间模式
    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getApplication() {
        return application;
    }

    /**
     * 初始化夜间模式
     */
    private void initNightMode() {
        // 设置主题
        if (!SpUtils.getBoolean(mContext, Constant.NIGHT_MODEL, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
