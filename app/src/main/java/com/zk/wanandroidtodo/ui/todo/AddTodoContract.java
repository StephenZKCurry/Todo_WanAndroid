package com.zk.wanandroidtodo.ui.todo;

import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;
import com.zk.wanandroidtodo.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @description: 添加待办清单Contract
 * @author: zhukai
 * @date: 2018/8/16 13:25
 */
public interface AddTodoContract {
    abstract class AddTodoPresenter extends BasePresenter<IAddTodoModel, IAddTodoView> {
        /**
         * 添加待办清单
         *
         * @param title   标题
         * @param content 内容
         * @param date    日期
         * @param type    类型，传0
         */
        public abstract void addTodo(String title, String content, String date, int type);
    }

    interface IAddTodoModel extends IBaseModel {
        /**
         * 添加待办清单
         *
         * @param title   标题
         * @param content 内容
         * @param date    日期
         * @param type    类型，传0
         */
        Observable<DataResponse> addTodo(String title, String content, String date, int type);
    }

    interface IAddTodoView extends IBaseView {
        /**
         * 添加待办清单成功
         *
         * @param message
         */
        void showAddTodoSuccess(String message);
    }
}
