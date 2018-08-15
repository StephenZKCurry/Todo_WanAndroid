package com.zk.wanandroidtodo.base.activity;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;

/**
 * @description: MVP Activity基类
 * @author: zhukai
 * @date: 2018/3/2 11:56
 */
public abstract class BaseMVPActivity<P extends BasePresenter, M extends IBaseModel> extends
        BaseActivity implements IBaseView {

    protected P mPresenter; // presenter,具体的presenter由子类确定
    protected M mIModel; // model,具体的model由子类确定

    @Override
    protected void initData() {
        super.initData();
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mIModel = (M) mPresenter.getModel();
            if (mIModel != null) {
                // 绑定model和view
                mPresenter.attachMV(mIModel, this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            // 解绑model和view
            mPresenter.detachMV();
        }
    }

    /**
     * 弹出Toast
     *
     * @param msg 要显示的toast消息字符串
     */
    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    @Override
    public void showProgressDialog(String msg) {
        super.showProgressDialog(msg);
    }

    /**
     * 隐藏提示框
     */
    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    /**
     * 请求成功
     *
     * @param message 成功信息
     */
    @Override
    public void showSuccess(String message) {
        showToast(message);
    }

    /**
     * 请求失败
     *
     * @param message 失败信息
     */
    @Override
    public void showFaild(String message) {
        showToast(getString(R.string.request_error));
    }

    /**
     * 显示当前网络不可用
     */
    @Override
    public void showNoNet() {
        showToast(getString(R.string.no_network_connection));
    }
}
