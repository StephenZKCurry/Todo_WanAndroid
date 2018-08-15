package com.zk.wanandroidtodo.manager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @description: 管理Rxjava注册订阅和取消订阅
 * @author: zhukai
 * @date: 2018/2/27 13:53
 */
public class RxManager {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable(); // 管理订阅者者

    /**
     * 注册订阅
     *
     * @param d
     */
    public void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
        mCompositeDisposable.dispose();
    }
}
