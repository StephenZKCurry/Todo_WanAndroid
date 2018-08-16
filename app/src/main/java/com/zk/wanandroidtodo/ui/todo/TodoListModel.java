package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.api.ApiService;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.TodoListBean;
import com.zk.wanandroidtodo.manager.RetrofitManager;
import com.zk.wanandroidtodo.utils.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 清单列表Model
 * @author: zhukai
 * @date: 2018/8/15 17:18
 */
public class TodoListModel implements TodoListContract.ITodoListModel {

    @NonNull
    public static TodoListModel newInstance() {
        return new TodoListModel();
    }

    /**
     * 获取清单列表
     *
     * @param page   页码，从1开始
     * @param isDone 是否已完成
     * @return
     */
    @Override
    public Observable<DataResponse<TodoListBean>> getTodoList(int page, boolean isDone) {
        if (isDone) {
            return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                    .getDoneList(page)
                    .compose(RxSchedulers.<DataResponse<TodoListBean>>applySchedulers());
        } else {
            return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                    .getTodoList(page)
                    .compose(RxSchedulers.<DataResponse<TodoListBean>>applySchedulers());
        }
    }

    /**
     * 删除清单
     *
     * @param id 唯一标识
     * @return
     */
    @Override
    public Observable<DataResponse> deleteTodo(int id) {
        return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                .deleteTodo(id)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }

    /**
     * 更新清单状态成功
     *
     * @param id     唯一标识
     * @param status 状态，0为未完成，1为完成
     * @return
     */
    @Override
    public Observable<DataResponse> updateTodoStatus(int id, int status) {
        return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                .updateTodoStatus(id, status)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}
