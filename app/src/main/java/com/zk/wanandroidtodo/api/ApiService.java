package com.zk.wanandroidtodo.api;

import com.zk.wanandroidtodo.bean.DataResponse;
import com.zk.wanandroidtodo.bean.TodoListBean;
import com.zk.wanandroidtodo.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @description: Retrofit请求接口
 * @author: zhukai
 * @date: 2018/8/15 10:05
 */
public interface ApiService {

    // 请求基地址
    public static final String BASE_URL = "http://wanandroid.com/";

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> doLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse<User>> doRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 未完成的Todo列表
     * http://www.wanandroid.com/lg/todo/listnotdo/0/json/1
     *
     * @param page 页码,从1开始
     * @return
     */
    @POST("/lg/todo/listnotdo/0/json/{page}")
    Observable<DataResponse<TodoListBean>> getTodoList(@Path("page") int page);

    /**
     * 已完成的Todo列表
     * http://www.wanandroid.com/lg/todo/listdone/0/json/1
     *
     * @param page
     * @return
     */
    @POST("/lg/todo/listdone/0/json/{path}")
    Observable<DataResponse<TodoListBean>> getDoneList(@Path("page") int page);

    /**
     * 新增一条Todo
     * http://www.wanandroid.com/lg/todo/add/json
     *
     * @param title   标题
     * @param content 内容
     * @param date    日期
     * @param type    类型
     * @return
     */
    @POST("/lg/todo/add/json")
    @FormUrlEncoded
    Observable<DataResponse> addTodo(@Field("title") String title,
                                     @Field("content") String content,
                                     @Field("date") String date,
                                     @Field("type") int type);
}
