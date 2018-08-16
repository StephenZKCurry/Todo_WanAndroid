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
 * @description: 登录Model
 * @author: zhukai
 * @date: 2018/8/15 10:11
 */
public class LoginModel implements LoginContract.ILoginModel {

    @NonNull
    public static LoginModel newInstance() {
        return new LoginModel();
    }

    @Override
    public Observable<DataResponse<UserBean>> doLogin(final String username, final String password) {
        return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                .doLogin(username, password)
                .filter(new Predicate<DataResponse<UserBean>>() {
                    @Override
                    public boolean test(DataResponse<UserBean> dataResponse) throws Exception {
                        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
                    }
                })
                .compose(RxSchedulers.<DataResponse<UserBean>>applySchedulers());
    }
}
