package com.zk.wanandroidtodo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.ui.mine.LoginActivity;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.SpUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import yanzhikai.textpath.SyncTextPathView;

/**
 * @description: 闪屏页面
 * @author: zhukai
 * @date: 2018/8/14 15:34
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tv_splash_text)
    SyncTextPathView tvSplashText;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        // 播放文字路径动画
        tvSplashText.startAnimation(0, 1);
        Observable.timer(4, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 跳转下一页
                        jumpNextPage();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        if (!TextUtils.isEmpty(SpUtils.getString(this, Constant.USER_ID, ""))) {
            // 已经登录,跳转首页（这里可能会存在cookie过期的情况，但本地仍然保存着用户信息）
            ActivityUtils.startActivity(this, new Intent(this, MainActivity.class));
        } else {
            // 未登录，跳转登录页
            ActivityUtils.startActivity(this, new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
