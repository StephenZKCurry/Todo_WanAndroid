package com.zk.wanandroidtodo.ui.setting;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.activity.BaseActivity;

/**
 * @description: 关于页面
 * @author: zhukai
 * @date: 2018/8/17 14:36
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.setting_title_about));
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }
}
