package com.zk.wanandroidtodo.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.wanandroidtodo.rxbus.RxBus;
import com.zk.wanandroidtodo.utils.ToastUtils;
import com.zk.wanandroidtodo.widgets.WaitPorgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description: 懒加载Fragmernt
 * @author: zhukai
 * @date: 2018/3/3 13:04
 */
public abstract class LazyFragment extends Fragment {

    /**
     * 说明：
     * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
     * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
     * 注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * 可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性，则viewpager会缓存指定数量的Fragment
     * 注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged
     * 注3: 针对初始就show的Fragment，为了触发onHiddenChanged事件达到lazy效果需要先hide再show
     */
    protected String title; // 标题
    protected String typeId; // 类型
    private boolean isVisible;  // 是否可见状态
    private boolean isPrepared; // 标志位，View已经初始化完成
    private boolean isFirstLoad = true; // 是否第一次加载
    protected Context mContext;
    protected WaitPorgressDialog mWaitPorgressDialog;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        RxBus.get().register(this); // 注册RxBus
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isFirstLoad = true;
        isPrepared = true;
        View view = inflater.inflate(getContentViewId(), null);
        initView(view);
        lazyLoad();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        RxBus.get().unRegister(mContext); // 取消注册RxBus
    }

    /**
     * 设置布局资源id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化页面
     */
    protected void initView(View view) {
        mWaitPorgressDialog = new WaitPorgressDialog(mContext);
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubId(String subId) {
        this.typeId = subId;
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * <p>
     * 注意：setUserVisibleHint(boolean isVisibleToUser)方法会多次回调,
     * 而且可能会在onCreateView()方法执行完毕之前回调.如果isVisibleToUser==true,
     * 然后进行数据加载和控件数据填充,但是onCreateView()方法并未执行完毕,此时就会出现NullPointerException空指针异常
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment为了触发该事件需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
        initEvent();
    }

    /**
     * 弹出Toast
     *
     * @param msg 要显示的toast消息字符串
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 显示提示框
     *
     * @param msg 等待消息字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitPorgressDialog.isShowing()) {
            mWaitPorgressDialog.dismiss();
        }
        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }
}
