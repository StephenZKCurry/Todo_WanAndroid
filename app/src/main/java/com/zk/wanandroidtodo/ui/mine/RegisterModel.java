package com.zk.wanandroidtodo.ui.mine;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zk.wanandroidtodo.api.ApiService;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.UserBean;
import com.zk.wanandroidtodo.manager.RetrofitManager;
import com.zk.wanandroidtodo.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @description: 注册Model
 * @author: zhukai
 * @date: 2018/3/12 16:36
 */
public class RegisterModel implements RegisterContract.IRegisterModel {

    @NonNull
    public static RegisterModel newInstance() {
        return new RegisterModel();
    }

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @Override
    public Observable<DataResponse<UserBean>> doRegister(final String username, final String password, final String repassword) {
        return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                .doRegister(username, password, repassword)
                .filter(new Predicate<DataResponse<UserBean>>() {
                    @Override
                    public boolean test(DataResponse<UserBean> dataResponse) throws Exception {
                        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                                && !TextUtils.isEmpty(repassword);
                    }
                })
                .compose(RxSchedulers.<DataResponse<UserBean>>applySchedulers());
    }
}
