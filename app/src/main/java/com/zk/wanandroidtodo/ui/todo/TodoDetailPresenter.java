package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.MyApplication;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.NetworkUtils;

import io.reactivex.functions.Consumer;

/**
 * @description: 清单详情Presenter
 * @author: zhukai
 * @date: 2018/8/16 15:46
 */
public class TodoDetailPresenter extends TodoDetailContract.TodoDetailPresenter {

    @NonNull
    public static TodoDetailPresenter newInstance() {
        return new TodoDetailPresenter();
    }

    @Override
    public TodoDetailContract.ITodoDetailModel getModel() {
        return TodoDetailModel.newInstance();
    }

    /**
     * 更新待办清单内容
     *
     * @param id      唯一标识
     * @param title   标题
     * @param content 详情
     * @param date    日期
     * @param status  状态，传0
     * @param type    类型，传0
     */
    @Override
    public void updateTodo(int id, String title, String content, String date, int status, int type) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.updateTodo(id, title, content, date, status, type)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 更新待办清单成功
                            mIView.showUpdateTodoSuccess(MyApplication.getContext().getString(R.string.update_todo_success));
                        } else {
                            // 更新待办清单失败
                            mIView.showToast(MyApplication.getContext().getString(R.string.update_todo_failed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        boolean available = NetworkUtils.isAvailable(MyApplication.getContext());
                        if (!available) {
                            mIView.showNoNet();
                        } else {
                            mIView.showFaild(throwable.getMessage());
                        }
                    }
                }));
    }
}
