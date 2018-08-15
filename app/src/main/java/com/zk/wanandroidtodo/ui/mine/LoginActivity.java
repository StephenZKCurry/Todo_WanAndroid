package com.zk.wanandroidtodo.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.activity.BaseMVPActivity;
import com.zk.wanandroidtodo.bean.User;
import com.zk.wanandroidtodo.ui.MainActivity;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 登录页面
 * @author: zhukai
 * @date: 2018/8/15 9:56
 */
public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter, LoginContract.ILoginModel>
        implements LoginContract.ILoginView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                // 登录
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showToast(getString(R.string.register_username));
                } else if (TextUtils.isEmpty(password)) {
                    showToast(getString(R.string.register_password));
                } else {
                    mPresenter.doLogin(username, password);
                }
                break;
            case R.id.tv_register:
                // 注册
                ActivityUtils.startActivity(mContext, new Intent(mContext, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登录成功
     *
     * @param user 用户信息
     */
    @Override
    public void showLoginSuccess(User user) {
        showToast(getString(R.string.login_success));
        // 保存用户信息
        SpUtils.setString(mContext, Constant.USER_ID, user.getId() + "");
        SpUtils.setString(mContext, Constant.USER_NAME, user.getUsername());
        // 跳转主页面
        ActivityUtils.startActivity(mContext, new Intent(mContext, MainActivity.class));
        ActivityUtils.finishActivity(mContext);
    }

    /**
     * 登录失败
     *
     * @param message 失败信息
     */
    @Override
    public void showLoginFaild(String message) {
        showToast(message);
    }
}
