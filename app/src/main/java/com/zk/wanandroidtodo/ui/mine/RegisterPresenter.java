package com.zk.wanandroidtodo.ui.mine;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.base.MyApplication;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.User;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.NetworkUtils;

import io.reactivex.functions.Consumer;

/**
 * @description: 注册Presenter
 * @author: zhukai
 * @date: 2018/3/12 16:35
 */
public class RegisterPresenter extends RegisterContract.RegisterPresenter {

    @NonNull
    public static RegisterPresenter newInstance() {
        return new RegisterPresenter();
    }

    @Override
    public RegisterContract.IRegisterModel getModel() {
        return RegisterModel.newInstance();
    }

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     */
    @Override
    public void doRegister(String username, String password, String repassword) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mIView.showProgressDialog("请稍后...");
        mRxManager.register(mIModel.doRegister(username, password, repassword)
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 注册成功
                            mIView.showRegisterSuccess(dataResponse.getData());
                        } else {
                            // 注册失败
                            mIView.showRegisterFaild(dataResponse.getErrorMsg());
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
