package com.zk.wanandroidtodo.ui.mine;

import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.UserBean;

import io.reactivex.Observable;

/**
 * @description: 登录Contract
 * @author: zhukai
 * @date: 2018/8/15 9:56
 */
public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        /**
         * 用户登录
         *
         * @param username 用户名
         * @param password 密码
         */
        public abstract void doLogin(String username, String password);
    }

    interface ILoginModel extends IBaseModel {
        /**
         * 用户登录
         *
         * @param username 用户名
         * @param password 密码
         * @return
         */
        Observable<DataResponse<UserBean>> doLogin(String username, String password);
    }

    interface ILoginView extends IBaseView {
        /**
         * 登录成功
         *
         * @param user 用户信息
         */
        void showLoginSuccess(UserBean user);

        /**
         * 登录失败
         *
         * @param message 失败信息
         */
        void showLoginFaild(String message);
    }
}
