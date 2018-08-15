package com.zk.wanandroidtodo.widgets;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @description: 等待提示对话框
 * @author: zhukai
 * @date: 2018/3/2 11:48
 */
public class WaitPorgressDialog extends ProgressDialog {

    public WaitPorgressDialog(Context context) {
        this(context, 0);
    }

    public WaitPorgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }
}
