package com.zk.wanandroidtodo.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.base.MyApplication;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.UserBean;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.NetworkUtils;

import io.reactivex.functions.Consumer;

/**
 * @description: 登录Presenter
 * @author: zhukai
 * @date: 2018/8/15 10:12
 */
public class LoginPresenter extends LoginContract.LoginPresenter {

    @NonNull
    public static LoginPresenter newInstance() {
        return new LoginPresenter();
    }

    @Override
    public LoginContract.ILoginModel getModel() {
        return LoginModel.newInstance();
    }

    @Override
    public void doLogin(String username, String password) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后...");
        mRxManager.register(mIModel.doLogin(username, password)
                .subscribe(new Consumer<DataResponse<UserBean>>() {
                    @Override
                    public void accept(DataResponse<UserBean> dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 登录成功
                            mIView.showLoginSuccess(dataResponse.getData());
                        } else {
                            // 登录失败
                            mIView.showLoginFaild(dataResponse.getErrorMsg());
                        }
                        mIView.hideProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(MyApplication.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                        mIView.hideProgressDialog();
                    }
                }));
    }
}
