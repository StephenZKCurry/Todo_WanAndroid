package com.zk.wanandroidtodo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zk.wanandroidtodo.R;
import com.zk.wanandroidtodo.base.activity.BaseActivity;
import com.zk.wanandroidtodo.ui.list.DoneListFragment;
import com.zk.wanandroidtodo.ui.list.TodoListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.rb_todo)
    RadioButton rbTodo;
    @BindView(R.id.rb_done)
    RadioButton rbDone;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    private List<Fragment> mFragments;
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "select_item";
    private static final int FRAGMENT_TODO = 0;
    private static final int FRAGMENT_DONE = 1;
    private static final int FRAGMENT_SETTING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // 恢复recreate前的位置
            mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
            rgBottomBar.check(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            // 默认显示首页
            mViewPager.setCurrentItem(FRAGMENT_TODO);
            rbTodo.setChecked(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // recreate时记录当前位置(在Manifest已禁止Activity旋转,所以旋转屏幕并不会执行以下代码)
        outState.putInt(POSITION, mViewPager.getCurrentItem());
        outState.putInt(SELECT_ITEM, rgBottomBar.getCheckedRadioButtonId());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.main_title_todo));
        mFragments = new ArrayList<>();
        mFragments.add(TodoListFragment.newInstance());
        mFragments.add(DoneListFragment.newInstance());
        mFragments.add(SettingFragment.newInstance());
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case FRAGMENT_TODO:
                        tvTitle.setText(getString(R.string.main_title_todo));
                        rbTodo.setChecked(true);
                        break;
                    case FRAGMENT_DONE:
                        tvTitle.setText(getString(R.string.main_title_done));
                        rbDone.setChecked(true);
                        break;
                    case FRAGMENT_SETTING:
                        tvTitle.setText(getString(R.string.main_title_setting));
                        rbSetting.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // RadioButton选中
        rgBottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_todo:
                        mViewPager.setCurrentItem(FRAGMENT_TODO);
                        break;
                    case R.id.rb_done:
                        mViewPager.setCurrentItem(FRAGMENT_DONE);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(FRAGMENT_SETTING);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // 按两次返回键退出
    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.ACTION_DOWN == event.getAction() && keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time) > 2000) {
                showToast(getString(R.string.main_alert_back));
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return false;
    }

    /**
     * ViewPager Adapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
