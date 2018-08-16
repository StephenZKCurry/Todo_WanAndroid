package com.zk.wanandroidtodo.ui.mine;

import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.UserBean;

import io.reactivex.Observable;

/**
 * @description: 注册Contract
 * @author: zhukai
 * @date: 2018/3/12 15:50
 */
public interface RegisterContract {

    abstract class RegisterPresenter extends BasePresenter<IRegisterModel, IRegisterView> {
        /**
         * 注册
         *
         * @param username   用户名
         * @param password   密码
         * @param repassword 确认密码
         */
        public abstract void doRegister(String username, String password, String repassword);
    }

    interface IRegisterModel extends IBaseModel {
        /**
         * 注册
         *
         * @param username   用户名
         * @param password   密码
         * @param repassword 确认密码
         * @return
         */
        Observable<DataResponse<UserBean>> doRegister(String username, String password, String repassword);
    }

    interface IRegisterView extends IBaseView {
        /**
         * 注册成功
         *
         * @param user 用户信息
         */
        void showRegisterSuccess(UserBean user);

        /**
         * 注册失败
         *
         * @param message 失败信息
         */
        void showRegisterFaild(String message);
    }
}
