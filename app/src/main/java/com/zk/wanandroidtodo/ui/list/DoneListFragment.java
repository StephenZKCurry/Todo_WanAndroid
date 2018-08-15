package com.zk.wanandroidtodo.ui.list;

import android.os.Bundle;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.fragment.BaseFragment;

/**
 * @description: 已完成事项列表页面
 * @author: zhukai
 * @date: 2018/8/15 11:41
 */
public class DoneListFragment extends BaseFragment {

    public static DoneListFragment newInstance() {
        Bundle args = new Bundle();
        DoneListFragment fragment = new DoneListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_done;
    }
}
