package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.activity.BaseMVPActivity;
import com.zk.wanandroidtodo.rxbus.RxBus;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.utils.TimeUtils;
import com.zk.wanandroidtodo.widgets.ContainsEmojiEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * @description: 添加待办清单页面
 * @author: zhukai
 * @date: 2018/8/16 13:15
 */
public class AddTodoActivity extends BaseMVPActivity<AddTodoContract.AddTodoPresenter, AddTodoContract.IAddTodoModel>
        implements AddTodoContract.IAddTodoView {

    @BindView(R.id.et_title)
    ContainsEmojiEditText etTitle;
    @BindView(R.id.et_content)
    ContainsEmojiEditText etContent;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return AddTodoPresenter.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_todo;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.add_todo_title));
        // 日期默认为当天
        tvDate.setText(TimeUtils.getCurrentTime().substring(0, 10));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @OnClick({R.id.tv_date, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                // 选择日期
                createDatePicker();
                break;
            case R.id.tv_submit:
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String date = tvDate.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    showToast(getString(R.string.add_todo_title_hint));
                } else {
                    // 添加待办清单
                    mPresenter.addTodo(title, content, date, 0);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加待办清单成功
     *
     * @param message
     */
    @Override
    public void showAddTodoSuccess(String message) {
        showToast(message);
        // 通知列表页刷新数据
        RxBus.get().send(Constant.RX_BUS_CODE_REFRESH);
        ActivityUtils.finishActivity(mContext);
    }

    /**
     * 选择日期
     */
    private void createDatePicker() {
        DatePicker picker = new DatePicker(this);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        picker.setRangeStart(currentYear, currentMonth + 1, currentDay);
        picker.setRangeEnd(2030, 12, 31);
        picker.setSelectedItem(currentYear, currentMonth + 1, currentDay);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvDate.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }
}
