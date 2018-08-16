package com.zk.wanandroidtodo.ui.todo;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.BasePresenter;
import com.zk.wanandroidtodo.base.activity.BaseMVPActivity;
import com.zk.wanandroidtodo.bean.TodoListBean;
import com.zk.wanandroidtodo.rxbus.RxBus;
import com.zk.wanandroidtodo.utils.ActivityUtils;
import com.zk.wanandroidtodo.utils.Constant;
import com.zk.wanandroidtodo.widgets.ContainsEmojiEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * @description: 清单详情页面
 * @author: zhukai
 * @date: 2018/8/16 15:40
 */
public class TodoDetailActivity extends BaseMVPActivity<TodoDetailContract.TodoDetailPresenter, TodoDetailContract.ITodoDetailModel>
        implements TodoDetailContract.ITodoDetailView {

    @BindView(R.id.et_title)
    ContainsEmojiEditText etTitle;
    @BindView(R.id.et_content)
    ContainsEmojiEditText etContent;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_submit)
    RoundTextView tvSubmit;

    private TodoListBean.DatasBean todoData;
    public static final String TODO_DETAIL = "todo_detail";
    public static final String IS_DONE = "is_done";

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return TodoDetailPresenter.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_todo_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.todo_detail_title));
    }

    @Override
    protected void initData() {
        super.initData();
        todoData = (TodoListBean.DatasBean) getIntent().getSerializableExtra(TODO_DETAIL);
        etTitle.setText(todoData.getTitle());
        etContent.setText(todoData.getContent());
        tvDate.setText(todoData.getDateStr());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!getIntent().getBooleanExtra(IS_DONE, false)) {
            getMenuInflater().inflate(R.menu.menu_detail, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            etTitle.setEnabled(true);
            etContent.setEnabled(true);
            tvDate.setEnabled(true);
            tvSubmit.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
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
                    // 更新待办清单
                    mPresenter.updateTodo(todoData.getId(), title, content, date, 0, 0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showUpdateTodoSuccess(String message) {
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
