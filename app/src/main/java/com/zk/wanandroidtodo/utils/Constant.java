package com.zk.wanandroidtodo.utils;

/**
 * @description: 常量
 * @author: zhukai
 * @date: 2018/2/27 13:29
 */
public class Constant {

    // 每页数量
    public static final int PAGE_SIZE = 20;

    // 加载数据类型
    public static final int TYPE_REFRESH_SUCCESS = 1;
    public static final int TYPE_REFRESH_ERROR = 2;
    public static final int TYPE_LOAD_MORE_SUCCESS = 3;
    public static final int TYPE_LOAD_MORE_ERROR = 4;

    // 请求数据返回码
    public static final int REQUEST_SUCCESS = 0;  // 请求成功
    public static final int REQUEST_FAIL = -1;  // 请求失败

    // 用户信息
    public static final String USER_ID = "user_id"; // 用户id
    public static final String USER_NAME = "user_name"; // 用户名
    public static final String USER_PASSWORD = "user_password"; // 密码

    // RxBus
    public final static int RX_BUS_CODE_REFRESH = 0; // 新增或更新清单内容，刷新列表
    public final static int RX_BUS_CODE_REFRESH_STATUS = 1; // 更新清单状态，刷新列表

    // 夜间模式
    public static final String NIGHT_MODEL = "night_model";
}
