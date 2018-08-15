package com.zk.wanandroidtodo.ui.list;

import android.os.Bundle;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.fragment.BaseFragment;

/**
 * @description: 待办事项列表页面
 * @author: zhukai
 * @date: 2018/8/15 11:41
 */
public class TodoListFragment extends BaseFragment {

    public static TodoListFragment newInstance() {
        Bundle args = new Bundle();
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_todo;
    }
}
