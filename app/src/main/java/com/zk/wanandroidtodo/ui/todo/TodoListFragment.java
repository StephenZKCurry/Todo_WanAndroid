package com.zk.wanandroidtodo.ui.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.fragment.BaseMVPFragment;
import com.zk.wanandroidtodo.bean.TodoListBean;
import com.zk.wanandroidtodo.bean.TodoSection;
import com.zk.wanandroidtodo.rxbus.RxBus;
import com.zk.wanandroidtodo.rxbus.Subscribe;
import com.zk.wanandroidtodo.rxbus.ThreadMode;
import com.zk.wanandroidtodo.ui.mine.LoginActivity;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.SpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description: 清单列表页面
 * @author: zhukai
 * @date: 2018/8/15 11:41
 */
public class TodoListFragment extends BaseMVPFragment<TodoListContract.TodoListPresenter, TodoListContract.ITodoListModel>
        implements TodoListContract.ITodoListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.refresh_todo)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_todo)
    RecyclerView mRecyclerView;

    private TodoListAdapter mAdapter;
    private boolean isDone; // 是否已完成
    private static final String IS_DONE = "is_done";

    public static TodoListFragment newInstance(boolean isDone) {
        Bundle args = new Bundle();
        args.putBoolean(IS_DONE, isDone);
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isDone = bundle.getBoolean(IS_DONE);
        }
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return new TodoListPresenter(isDone);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_todo;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_main));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new TodoListAdapter(null, isDone);
        // 设置空数据显示
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getTodoList(1, isDone);
        showRefreshView(true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void showTodoList(TodoListBean todoListBean, int loadType) {
        List<TodoSection> todoSections = new ArrayList<>();
        for (int i = 0; i < todoListBean.getDatas().size(); i++) {
            // 去掉相同的头部
            if (i == 0 || !todoListBean.getDatas().get(i).getDateStr()
                    .equals(todoListBean.getDatas().get(i - 1).getDateStr())) {
                todoSections.add(new TodoSection(true, todoListBean.getDatas().get(i).getDateStr()));
            }
            todoSections.add(new TodoSection(todoListBean.getDatas().get(i)));
        }
        switch (loadType) {
            case Constant.TYPE_REFRESH_SUCCESS:
                // 刷新
                mAdapter.setNewData(todoSections);
                break;
            case Constant.TYPE_LOAD_MORE_SUCCESS:
                // 加载更多
                mAdapter.addData(todoSections);
                break;
            default:
                break;
        }
        if (todoListBean.getDatas() == null || todoListBean.getDatas().isEmpty() ||
                todoListBean.getDatas().size() < Constant.PAGE_SIZE) {
            // 没有更多数据了
            mAdapter.loadMoreEnd();
        } else {
            // 加载完成
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showRefreshView(final Boolean refresh) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    /**
     * 删除清单成功
     *
     * @param position 清单在列表中的position，用于更新数据
     */
    @Override
    public void deleteTodoSuccess(int position) {
        showToast(getString(R.string.delete_todo_success));
        // 更新数据
        // 这里当只有一条数据时，调用remove()方法，仍然会显示“没有更多数据”
//        mAdapter.remove(position);
        mPresenter.refresh();
    }

    /**
     * 更新清单状态成功
     *
     * @param position 清单在列表中的position，用于更新数据
     */
    @Override
    public void updateTodoStatusSuccess(int position) {
//        mAdapter.remove(position);
        // 通知列表页刷新数据
        RxBus.get().send(Constant.RX_BUS_CODE_REFRESH_STATUS);
    }

    /**
     * 登录过期
     *
     * @param message 提示信息
     */
    @Override
    public void showLoginExpired(String message) {
        showToast(message);
        // 清除本地保存的用户信息
//        SpUtils.clearSp(mContext);
        SpUtils.setString(mContext, Constant.USER_ID, "");
        SpUtils.setString(mContext, Constant.USER_NAME, "");
        // 跳转登录页面
        ActivityUtils.startActivity(mContext, new Intent(mContext, LoginActivity.class));
        ActivityUtils.finishActivity(mContext);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 头部不设置点击事件
        if (!mAdapter.getItem(position).isHeader) {
            // 跳转清单详情页面
            Intent intent = new Intent(mContext, TodoDetailActivity.class);
            intent.putExtra(TodoDetailActivity.TODO_DETAIL, (Serializable) mAdapter.getItem(position).t);
            intent.putExtra(TodoDetailActivity.IS_DONE, isDone);
            ActivityUtils.startActivity(mContext, intent);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.iv_done:
                new MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_update_todo_status)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                if (isDone) {
                                    // 将清单状态重置为待办
                                    mPresenter.updateTodoStatus(position, mAdapter.getItem(position).t.getId(), 0);
                                } else {
                                    // 完成清单
                                    mPresenter.updateTodoStatus(position, mAdapter.getItem(position).t.getId(), 1);
                                }
                            }
                        }).show();
                break;
            case R.id.iv_delete:
                new MaterialDialog.Builder(mContext)
                        .content(R.string.confirm_delete_todo)
                        .positiveText(R.string.dialog_confirm)
                        .negativeText(R.string.dialog_cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                // 删除清单
                                mPresenter.deleteTodo(position, mAdapter.getItem(position).t.getId());
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 新增或更新待办清单成功，刷新列表
     */
    @Subscribe(code = Constant.RX_BUS_CODE_REFRESH, threadMode = ThreadMode.MAIN)
    public void refreshTodoList() {
        if (!isDone) {
            // 只刷新待办清单页面
            mPresenter.refresh();
        }
    }

    /**
     * 更新清单状态成功，刷新列表
     */
    @Subscribe(code = Constant.RX_BUS_CODE_REFRESH_STATUS, threadMode = ThreadMode.MAIN)
    public void refreshList() {
        mPresenter.refresh();
    }
}
