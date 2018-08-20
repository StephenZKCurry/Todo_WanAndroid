package com.zk.wanandroidtodo.ui.setting;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.activity.BaseActivity;
import com.zk.wanandroidtodo.widgets.WebViewFragment;

import butterknife.BindView;

/**
 * @description: 项目主页（WebView）
 * @author: zhukai
 * @date: 2018/8/17 14:14
 */
public class ProjecActivity extends BaseActivity {

    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;
    private WebView mWebView;
    private WebViewFragment webViewFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_project;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.setting_title_github));
        webViewFragment = new WebViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, webViewFragment, WebViewFragment.class.getName()).commit();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 由于Fragment的onCreateView()在Activity的onCreat()之后调用，所以不能在init()方法中获取到WebView
        mWebView = webViewFragment.getWebView();
        initWebView(mWebView, "https://github.com/StephenZKCurry/Todo_WanAndroid");
    }

    /**
     * 初始化WebView
     *
     * @param webView
     * @param url     加载的地址
     */
    private void initWebView(WebView webView, String url) {
        // 设置WebView参数
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 支持js
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 显示放大缩小按钮
        settings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
        // 设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("网页开始加载");
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                mProgressBar.setVisibility(View.GONE);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
        webView.loadUrl(url);
    }
}
