package com.zk.wanandroidtodo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.fragment.BaseFragment;
import com.zk.wanandroidtodo.ui.mine.LoginActivity;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 设置页面
 * @author: zhukai
 * @date: 2018/8/15 13:36
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.tv_username_head)
    TextView tvUsernameHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_nightmode)
    RelativeLayout rlNightmode;
    @BindView(R.id.rl_github)
    RelativeLayout rlGithub;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        if (!TextUtils.isEmpty(SpUtils.getString(mContext, Constant.USER_NAME, ""))) {
            tvUsernameHead.setText(SpUtils.getString(mContext, Constant.USER_NAME, "").substring(0, 1));
            tvUsername.setText(SpUtils.getString(mContext, Constant.USER_NAME, ""));
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @OnClick({R.id.rl_nightmode, R.id.rl_github, R.id.rl_about, R.id.rl_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_nightmode:
                // 切换主题
                switchNight(SpUtils.getBoolean(mContext, Constant.NIGHT_MODEL, false));
                break;
            case R.id.rl_github:

                break;
            case R.id.rl_about:

                break;
            case R.id.rl_logout:
                // 退出登录
                new MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_logout)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                // 清除本地保存的用户信息
//                                SpUtils.clearSp(mContext);
                                SpUtils.setString(mContext, Constant.USER_ID, "");
                                SpUtils.setString(mContext, Constant.USER_NAME, "");
                                // 清除cookies,不清除的话会保持登录状态，请求时还回携带cookie
                                new SharedPrefsCookiePersistor(mContext).clear();
                                // 跳转登录页
                                ActivityUtils.startActivity(mContext, new Intent(mContext, LoginActivity.class));
                                ActivityUtils.finishActivity(mContext);
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 切换主题
     *
     * @param isNight 是否为夜间模式
     */
    private void switchNight(final boolean isNight) {
        if (isNight) {
            // 切换为正常模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SpUtils.setBoolean(mContext, Constant.NIGHT_MODEL, false);
        } else {
            // 切换为夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SpUtils.setBoolean(mContext, Constant.NIGHT_MODEL, true);
        }
        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        getActivity().recreate();
    }
}
