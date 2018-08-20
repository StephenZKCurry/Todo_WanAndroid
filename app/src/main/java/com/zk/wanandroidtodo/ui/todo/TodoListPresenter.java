package com.zk.wanandroidtodo.ui.todo;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.MyApplication;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.TodoListBean;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.ErrorAction;

import io.reactivex.functions.Consumer;

/**
 * @description: 清单列表Presenter
 * @author: zhukai
 * @date: 2018/8/15 17:17
 */
public class TodoListPresenter extends TodoListContract.TodoListPresenter {

    private boolean isDone; // 是否已完成
    private int mPage; // 当前页码

    public TodoListPresenter(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public TodoListContract.ITodoListModel getModel() {
        return TodoListModel.newInstance();
    }

    /**
     * 下拉刷新数据
     */
    @Override
    public void refresh() {
        mPage = 1;
        getTodoList(mPage, isDone);
        mIView.showRefreshView(true);
    }

    /**
     * 上拉加载数据
     */
    @Override
    public void loadMore() {
        mPage++;
        getTodoList(mPage, isDone);
    }

    /**
     * 获取清单列表
     *
     * @param page   页码，从1开始
     * @param isDone 是否已完成
     */
    @Override
    public void getTodoList(final int page, boolean isDone) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.getTodoList(page, isDone)
                .subscribe(new Consumer<DataResponse<TodoListBean>>() {
                    @Override
                    public void accept(DataResponse<TodoListBean> dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            if (page == 1) {
                                // 下拉刷新
                                mIView.showTodoList(dataResponse.getData(), Constant.TYPE_REFRESH_SUCCESS);
                            } else {
                                // 上拉加载
                                mIView.showTodoList(dataResponse.getData(), Constant.TYPE_LOAD_MORE_SUCCESS);
                            }
                        } else {
                            if (dataResponse.getErrorMsg().equals("请先登录！")) {
                                mIView.showLoginExpired(MyApplication.getContext().getString(R.string.login_expired));
                            }
                        }
                        mIView.showRefreshView(false);
                    }
                }, new ErrorAction() {
                    @Override
                    public void onNetError(Throwable throwable) {
                        mIView.showNoNet();
                        mIView.showRefreshView(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mIView.showFaild(throwable.getMessage());
                        mIView.showRefreshView(false);
                    }
                }));
    }

    /**
     * 删除清单
     *
     * @param id       唯一标识
     * @param position 清单在列表中的position，用于更新数据
     */
    @Override
    public void deleteTodo(final int position, int id) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.deleteTodo(id)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 删除清单成功
                            mIView.deleteTodoSuccess(position);
                        } else {
                            // 删除清单失败
                            mIView.showToast(MyApplication.getContext().getString(R.string.delete_todo_failed));
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    public void onNetError(Throwable throwable) {
                        mIView.showNoNet();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mIView.showFaild(throwable.getMessage());
                    }
                }));
    }

    /**
     * 更新清单状态
     *
     * @param position 清单在列表中的position，用于更新数据
     * @param id       唯一标识
     * @param status   状态，0为未完成，1为完成
     */
    @Override
    public void updateTodoStatus(final int position, int id, int status) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.updateTodoStatus(id, status)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 更新清单状态成功
                            mIView.updateTodoStatusSuccess(position);
                        } else {
                            // 更新清单状态失败
                            mIView.showToast(MyApplication.getContext().getString(R.string.update_todo_status_failed));
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    public void onNetError(Throwable throwable) {
                        mIView.showNoNet();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mIView.showFaild(throwable.getMessage());
                    }
                }));
    }
}
