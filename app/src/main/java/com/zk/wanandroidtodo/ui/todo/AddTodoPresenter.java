package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.MyApplication;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.ErrorAction;

import io.reactivex.functions.Consumer;

/**
 * @description: 添加待办清单Presenter
 * @author: zhukai
 * @date: 2018/8/16 13:33
 */
public class AddTodoPresenter extends AddTodoContract.AddTodoPresenter {

    @NonNull
    public static AddTodoPresenter newInstance() {
        return new AddTodoPresenter();
    }

    @Override
    public AddTodoContract.IAddTodoModel getModel() {
        return AddTodoModel.newInstance();
    }

    /**
     * 添加待办清单
     *
     * @param title   标题
     * @param content 内容
     * @param date    日期
     * @param type    类型，传0
     */
    @Override
    public void addTodo(String title, String content, String date, int type) {
        if (mIView == null || mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.addTodo(title, content, date, type)
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            // 添加待办清单成功
                            mIView.showAddTodoSuccess(MyApplication.getContext().getString(R.string.add_todo_success));
                        } else {
                            // 添加待办清单失败
                            mIView.showToast(MyApplication.getContext().getString(R.string.add_todo_failed));
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
