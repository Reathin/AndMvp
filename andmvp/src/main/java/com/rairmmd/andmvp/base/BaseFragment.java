package com.rairmmd.andmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.gyf.barlibrary.ImmersionBar;
import com.rairmmd.andmvp.anno.BindEventBus;
import com.rairmmd.andmvp.utils.AppUtils;
import com.rairmmd.andmvp.utils.DensityUtils;
import com.rairmmd.andmvp.utils.EventBusUtils;
import com.rairmmd.andmvp.utils.VersionUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:BaseFragment
 */
public abstract class BaseFragment<P extends IPresent> extends SupportFragment implements IView<P> {

    private P p;
    protected Activity context;
    private View rootView;
    protected LayoutInflater layoutInflater;

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private LoadingDailog loadingDailog;
    protected FragmentManager fragmentManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null && getLayoutId() > 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            bindUI(rootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        mImmersionBar = ImmersionBar.with(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getP();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        fragmentManager = getFragmentManager();
        initView(savedInstanceState);
        initData();
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (getP() != null) {
            getP().detachV();
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        p = null;
        unbinder.unbind();
    }

    @Override
    public int getOptionMenuId() {
        return 0;
    }

    /**
     * 设置toolbar
     *
     * @param title     标题
     * @param isCanBack 是否设置返回键
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isCanBack) {
        toolbar.setTitle(title);
        if ((getActivity()) != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dp2px(4f));
        }
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (isCanBack && supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
    }

    /**
     * 设置toolbar
     *
     * @param title          标题
     * @param isElevation    是否设置阴影
     * @param isCanBack      是否设置返回键
     * @param isImmersionBar 是否设置沉浸状态栏
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isElevation, boolean isCanBack, boolean isImmersionBar) {
        toolbar.setTitle(title);
        if ((getActivity()) != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        if (VersionUtils.isLollipop() && isElevation) {
            toolbar.setElevation(DensityUtils.dp2px(4f));
        }
        if (isImmersionBar) {
            mImmersionBar.titleBar(toolbar).init();
        }
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (isCanBack && supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
    }

    /**
     * 设置toolbar
     *
     * @param title 标题
     */
    protected void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        if ((getActivity()) != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        if (VersionUtils.isLollipop()) {
            toolbar.setElevation(DensityUtils.dp2px(4f));
        }
    }

    /**
     * 透明状态栏
     *
     * @param toolbar toolbar
     */
    protected void translucentStatus(Toolbar toolbar) {
        mImmersionBar.titleBar(toolbar).init();
    }

    protected void translucentStatus() {
        mImmersionBar.init();
    }

    @Nullable
    @Override
    public Activity getContext() {
        return context;
    }

    /**
     * 加载dialog
     */
    public void showLoading() {
        loadingDailog = new LoadingDailog.Builder(context).setCancelable(true)
                .setShowMessage(false).setCancelOutside(false).create();
        loadingDailog.show();
    }

    /**
     * 加载dialog
     */
    public void showLoading(String message) {
        loadingDailog = new LoadingDailog.Builder(context).setCancelable(true)
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
