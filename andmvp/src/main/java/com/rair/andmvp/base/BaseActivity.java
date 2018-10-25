package com.rair.andmvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.rair.andmvp.utils.AppUtils;
import com.rair.andmvp.utils.DensityUtils;
import com.rair.andmvp.utils.VersionUtils;

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

    private IDelegate mDelegate;
    private P p;
    protected Activity context;

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private MaterialDialog loadingDialog;
    private MaterialDialog messageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLayoutId());
        BaseApplication.getInstance().addActivity(this);
        mImmersionBar = ImmersionBar.with(this);
        bindUI(null);
        initView(savedInstanceState);
        initData();
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    protected IDelegate getIDelegate() {
        if (mDelegate == null) {
            mDelegate = BaseDelegate.create(context);
        }
        return mDelegate;
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

    @Override
    protected void onResume() {
        super.onResume();
        getIDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getIDelegate().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (getP() != null) {
            getP().detachV();
        }
        getIDelegate().destory();
        p = null;
        mDelegate = null;
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
    public void showLoadingDialog() {
        loadingDialog = new MaterialDialog.Builder(this)
                .progress(true, 0).canceledOnTouchOutside(false)
                .progressIndeterminateStyle(false).content("正在加载...")
                .show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 消息dialog
     */
    public void showMessageDialog(String message) {
        messageDialog = new MaterialDialog.Builder(this)
                .content(message).title("提示").show();
    }

    public void dismissMessageDialog() {
        if (messageDialog != null) {
            messageDialog.dismiss();
        }
    }

    /**
     * 显示toasty
     *
     * @param text 提示信息
     */
    public void showToasty(String text) {
        Toasty.info(AppUtils.getContext(), text, Toast.LENGTH_SHORT).show();
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
