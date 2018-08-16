package com.zk.wanandroidtodo.ui.todo;

import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;
import com.zk.wanandroidtodo.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @description: 清单详情Contract
 * @author: zhukai
 * @date: 2018/8/16 15:41
 */
public interface TodoDetailContract {
    abstract class TodoDetailPresenter extends BasePresenter<ITodoDetailModel, ITodoDetailView> {
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
        public abstract void updateTodo(int id, String title, String content, String date, int status, int type);
    }

    interface ITodoDetailModel extends IBaseModel {
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
        Observable<DataResponse> updateTodo(int id, String title, String content, String date, int status, int type);
    }

    interface ITodoDetailView extends IBaseView {
        /**
         * 更新待办清单成功
         *
         * @param message
         */
        void showUpdateTodoSuccess(String message);
    }
}
