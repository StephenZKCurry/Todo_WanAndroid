package com.zk.wanandroidtodo.ui.todo;

import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.IBaseModel;
import com.zk.wanandroidtodo.base.IBaseView;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.TodoListBean;

import io.reactivex.Observable;

/**
 * @description: 清单列表Contract
 * @author: zhukai
 * @date: 2018/8/15 16:53
 */
public interface TodoListContract {

    abstract class TodoListPresenter extends BasePresenter<ITodoListModel, ITodoListView> {
        /**
         * 获取清单列表
         *
         * @param page   页码，从1开始
         * @param isDone 是否已完成
         */
        public abstract void getTodoList(int page, boolean isDone);

        /**
         * 下拉刷新数据
         */
        public abstract void refresh();

        /**
         * 上拉加载数据
         */
        public abstract void loadMore();

        /**
         * 删除清单
         *
         * @param position 清单在列表中的position，用于更新数据
         * @param id       唯一标识
         */
        public abstract void deleteTodo(int position, int id);

        /**
         * 更新清单状态
         *
         * @param position 清单在列表中的position，用于更新数据
         * @param id       唯一标识
         * @param status   状态，0为未完成，1为完成
         */
        public abstract void updateTodoStatus(int position, int id, int status);
    }

    interface ITodoListModel extends IBaseModel {
        /**
         * 获取清单列表
         *
         * @param page   页码，从1开始
         * @param isDone 是否已完成
         * @return
         */
        Observable<DataResponse<TodoListBean>> getTodoList(int page, boolean isDone);

        /**
         * 删除清单
         *
         * @param id 唯一标识
         * @return
         */
        Observable<DataResponse> deleteTodo(int id);

        /**
         * 更新清单状态
         *
         * @param id     唯一标识
         * @param status 状态，0为未完成，1为完成
         * @return
         */
        Observable<DataResponse> updateTodoStatus(int id, int status);
    }

    interface ITodoListView extends IBaseView {
        /**
         * 显示清单列表
         *
         * @param todoListBean
         * @param loadType     类型：刷新或加载更多
         */
        void showTodoList(TodoListBean todoListBean, int loadType);

        /**
         * 是否显示刷新view，这里使用SwipeRefreshLayout
         * 也可以使用IBaseView中的showProgressDialog()方法，以对话框形式刷新
         *
         * @param refresh
         */
        void showRefreshView(Boolean refresh);

        /**
         * 删除清单成功
         *
         * @param position 清单在列表中的position，用于更新数据
         */
        void deleteTodoSuccess(int position);

        /**
         * 更新清单状态成功
         *
         * @param position 清单在列表中的position，用于更新数据
         */
        void updateTodoStatusSuccess(int position);

        /**
         * 提示登录过期
         *
         * @param message 提示信息
         */
        void showLoginExpired(String message);
    }
}
