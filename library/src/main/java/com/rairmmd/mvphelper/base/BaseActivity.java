package com.rairmmd.mvphelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.rairmmd.mvphelper.utils.AppUtils;
import com.rairmmd.mvphelper.utils.DensityUtils;
import com.rairmmd.mvphelper.utils.VersionUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:BaseActivity
 */
public abstract class BaseActivity<P extends IPresent> extends SupportActivity implements IView<P> {

    private P p;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        BaseApplication.getIns().addActivity(this);
        mImmersionBar = ImmersionBar.with(this);
        bindUI(null);
        initView();
        initData();
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getP() != null) {
            getP().detachV();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        p = null;
        BaseApplication.getIns().finishActivity(this);
        unbinder.unbind();
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
        if (isCanBack && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> ActivityCompat.finishAfterTransition(this));
        }
    }

    /**
     * 设置tool
     *
     * @param title               标题
     * @param isCanBack           是否给toolbar设置返回键
     * @param isTranslucentStatus 是否设置沉浸状态栏（白底黑字）
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isCanBack, boolean isTranslucentStatus) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dip2px(AppUtils.getContext(), 4));
        }
        if (isCanBack && getSupportActionBar() != null) {
            //返回键
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> ActivityCompat.finishAfterTransition(this));
        }
        if (isTranslucentStatus) {
            //透明状态栏
            mImmersionBar.titleBar(toolbar).statusBarDarkFont(true).init();
        }
    }

    /**
     * 设置tool
     *
     * @param title               标题
     * @param isSetElevation      是否设置阴影
     * @param isCanBack           是否设置返回键
     * @param isTranslucentStatus 是否设置沉浸状态栏
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isSetElevation, boolean isCanBack, boolean isTranslucentStatus) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (VersionUtils.isLollipop() && isSetElevation) {
            toolbar.setElevation(DensityUtils.dip2px(AppUtils.getContext(), 4));
        }
        if (isCanBack && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> ActivityCompat.finishAfterTransition(this));
        }
        if (isTranslucentStatus) {
            mImmersionBar.titleBar(toolbar).init();
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * 显示toast
     *
     * @param msg
     */
    private Toast toast;

    public void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toasty.info(AppUtils.getContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}