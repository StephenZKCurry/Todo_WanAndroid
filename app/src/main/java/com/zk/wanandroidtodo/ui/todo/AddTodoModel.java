package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;

import com.zk.wanandroidtodo.api.ApiService;
import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.manager.RetrofitManager;
import com.zk.wanandroidtodo.utils.RxSchedulers;

import io.reactivex.Observable;

/**
 * @description: 添加待办清单Model
 * @author: zhukai
 * @date: 2018/8/16 13:34
 */
public class AddTodoModel implements AddTodoContract.IAddTodoModel {

    @NonNull
    public static AddTodoModel newInstance() {
        return new AddTodoModel();
    }

    /**
     * 添加待办清单
     *
     * @param title   标题
     * @param content 内容
     * @param date    日期
     * @param type    类型，传0
     * @return
     */
    @Override
    public Observable<DataResponse> addTodo(String title, String content, String date, int type) {
        return RetrofitManager.createApi(ApiService.class, ApiService.BASE_URL)
                .addTodo(title, content, date, type)
                .compose(RxSchedulers.<DataResponse>applySchedulers());
    }
}
