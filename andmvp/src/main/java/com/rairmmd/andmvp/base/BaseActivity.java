package com.rairmmd.andmvp.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.gyf.barlibrary.ImmersionBar;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrPosition;
import com.rairmmd.andmvp.anno.BindEventBus;
import com.rairmmd.andmvp.utils.AppUtils;
import com.rairmmd.andmvp.utils.DensityUtils;
import com.rairmmd.andmvp.utils.EventBusUtils;
import com.rairmmd.andmvp.utils.VersionUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created on 2017/9/7
 *
 * @author Rair.
 * @desc: Activity基类，采用MVP模式
 */
public abstract class BaseActivity<P extends IPresent> extends SupportActivity implements IView<P> {

    private P p;
    protected Activity context;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private LoadingDailog loadingDailog;
    protected SlidrInterface slidrInterface;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
        }
        if (getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        fragmentManager = getSupportFragmentManager();
        BaseApplication.getInstance().addActivity(this);
        mImmersionBar = ImmersionBar.with(this);
        slidrConfig();
        initView(savedInstanceState);
        initData();
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    protected P getP() {
        if (p == null) {
            p = newP();
        }
        if (p != null) {
            if (!p.hasV()) {
                p.attachV(this);
            }
        }
        return p;
    }

    private void slidrConfig() {
        SlidrConfig config = new SlidrConfig.Builder().position(SlidrPosition.LEFT).sensitivity(1f)
                .scrimColor(Color.TRANSPARENT).scrimStartAlpha(0.8f).scrimEndAlpha(0f).edge(true)
                .velocityThreshold(2400).distanceThreshold(0.25f).edgeSize(0.18f).build();
        slidrInterface = Slidr.attach(this, config);
        slidrInterface.lock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        if (getP() != null) {
            getP().detachV();
        }
        p = null;
        unbinder.unbind();
        BaseApplication.getInstance().finishActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionMenuId() > 0) {
            getMenuInflater().inflate(getOptionMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public int getOptionMenuId() {
        return 0;
    }

    /**
     * 设置海拔  阴影
     *
     * @param view      View
     * @param elevation 海拔
     */
    protected void setElevation(View view, float elevation) {
        ViewCompat.setElevation(view, DensityUtils.dp2px(elevation));
    }

    /**
     * 设置toolbar
     *
     * @param toolbar   toolbar
     * @param title     标题
     * @param isCanBack 返回键
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isCanBack) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dip2px(4f));
        }
        if (isCanBack && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.finishAfterTransition(context);
                }
            });
        }
    }

    /**
     * 设置tool
     *
     * @param title         标题
     * @param isCanBack     是否给toolbar设置返回键
     * @param isTransparent 是否设置沉浸状态栏（白底黑字）
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isCanBack, boolean isTransparent) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dip2px(4f));
        }
        if (isCanBack && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.finishAfterTransition(context);
                }
            });
        }
        if (isTransparent) {
            mImmersionBar.titleBar(toolbar).statusBarDarkFont(true).init();
        }
    }

    /**
     * 设置tool
     *
     * @param title         标题
     * @param isElevation   是否设置阴影
     * @param isCanBack     是否设置返回键
     * @param isTransparent 是否设置沉浸状态栏
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isCanBack, boolean isElevation, boolean isTransparent) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (VersionUtils.isLollipop() && isElevation) {
            toolbar.setElevation(DensityUtils.dip2px(4f));
        }
        if (isCanBack && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.finishAfterTransition(context);
                }
            });
        }
        if (isTransparent) {
            mImmersionBar.titleBar(toolbar).init();
        }
    }

    protected void translucentStatus() {
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true).init();
    }

    protected void translucentStatus(Toolbar toolbar) {
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dip2px(4f));
        }
        mImmersionBar.titleBar(toolbar).init();
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * 加载dialog
     */
    public void showLoading() {
        loadingDailog = new LoadingDailog.Builder(this).setCancelable(true)
                .setShowMessage(false).setCancelOutside(false).create();
        loadingDailog.show();
    }

    /**
     * 加载dialog
     */
    public void showLoading(String message) {
        loadingDailog = new LoadingDailog.Builder(this).setCancelable(true)
                .setShowMessage(true).setMessage(message).setCancelOutside(false).create();
        loadingDailog.show();
    }

    public void dismissLoading() {
        if (loadingDailog != null) {
            loadingDailog.dismiss();
        }
    }

    /**
     * 显示toasty
     *
     * @param text 提示信息
     */
    public void showToasty(String text) {
        Toasty.info(AppUtils.getContext(), text, Toast.LENGTH_SHORT, false).show();
    }

    /**
     * 显示Toast
     *
     * @param text 提示信息
     */
    public void showToast(String text) {
        Toast.makeText(AppUtils.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
