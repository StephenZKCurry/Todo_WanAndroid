package com.zk.wanandroidtodo.utils;

import com.zk.wanandroidtodo.base.MyApplication;

import io.reactivex.functions.Consumer;

/**
 * @description: 错误处理
 * @author: zhukai
 * @date: 2018/8/17 13:32
 */
public abstract class ErrorAction implements Consumer<Throwable> {

    /**
     * 网络错误
     *
     * @param throwable
     */
    public abstract void onNetError(Throwable throwable);

    /**
     * 不是由于设备网络原因导致的错误
     * 这里导致错误原因太多了，所以暂时都回调一个方法
     *
     * @param throwable
     */
    public abstract void onError(Throwable throwable);

    @Override
    public void accept(Throwable throwable) throws Exception {
        boolean available = NetworkUtils.isAvailable(MyApplication.getContext());
        if (!available) {
            onNetError(throwable);
        } else {
            onError(throwable);
        }
    }
}
