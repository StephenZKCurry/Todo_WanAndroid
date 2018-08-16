package com.zk.wanandroidtodo.ui.todo;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.bean.TodoSection;

import java.util.List;

/**
 * @description: 清单列表Adapter
 * @author: zhukai
 * @date: 2018/8/16 9:41
 */
public class TodoListAdapter extends BaseSectionQuickAdapter<TodoSection, BaseViewHolder> {

    private boolean isDone; // 是否已完成

    public TodoListAdapter(List<TodoSection> data, boolean isDone) {
        super(R.layout.item_todo, R.layout.item_todo_head, data);
        this.isDone = isDone;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, TodoSection item) {
        helper.setText(R.id.tv_date, item.header);
        if (isDone) {
            helper.setBackgroundColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_done_light));
            helper.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_done));
        } else {
            helper.setBackgroundColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_todo_light));
            helper.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.color_todo));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoSection item) {
        if (isDone) {
            helper.setImageResource(R.id.iv_done, R.mipmap.ic_reset);
            helper.setVisible(R.id.tv_date_done, true);
            helper.setText(R.id.tv_date_done, "完成时间：" + item.t.getCompleteDateStr());
        } else {
            helper.setImageResource(R.id.iv_done, R.mipmap.ic_complete);
            helper.setVisible(R.id.tv_date_done, false);
        }
        helper.setText(R.id.tv_title, item.t.getTitle());
        if (TextUtils.isEmpty(item.t.getContent())) {
            helper.setVisible(R.id.tv_content, false);
        } else {
            helper.setVisible(R.id.tv_content, true);
            helper.setText(R.id.tv_content, item.t.getContent());
        }

        // 添加点击事件
        helper.addOnClickListener(R.id.iv_done);
        helper.addOnClickListener(R.id.iv_delete);
    }
}
