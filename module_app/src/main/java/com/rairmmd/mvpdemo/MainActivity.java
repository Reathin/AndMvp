package com.rairmmd.mvpdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.rairmmd.andmvp.base.BaseActivity;
import com.rairmmd.mvpdemo.fragment.MainFragment;
import com.rairmmd.mvpdemo.present.MainPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolbar(toolbar, getString(R.string.app_name), false);
    }

    @Override
    public void initData() {
        loadRootFragment(R.id.fl_container, MainFragment.newInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
    }
}
