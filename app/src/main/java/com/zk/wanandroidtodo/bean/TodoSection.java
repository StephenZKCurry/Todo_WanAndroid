package com.zk.wanandroidtodo.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @description: 事项清单分组实体类
 * @author: zhukai
 * @date: 2018/8/16 9:36
 */
public class TodoSection extends SectionEntity<TodoListBean.DatasBean> {

    public TodoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TodoSection(TodoListBean.DatasBean datasBean) {
        super(datasBean);
    }
}
