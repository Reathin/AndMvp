package com.rair.andmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.rair.andmvp.utils.AppUtils;
import com.rair.andmvp.utils.DensityUtils;
import com.rair.andmvp.utils.VersionUtils;

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

    private IDelegate mDelegate;
    private P p;
    protected Activity context;
    private View rootView;
    protected LayoutInflater layoutInflater;

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;
    private MaterialDialog messageDialog;
    private MaterialDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        mImmersionBar = ImmersionBar.with(this);
        bindUI(rootView);
        return rootView;
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        initData();
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
        getIDelegate().destory();
        p = null;
        mDelegate = null;
        unbinder.unbind();
    }

    @Override
    public int getOptionMenuId() {
        return 0;
    }

    @Nullable
    @Override
    public Activity getContext() {
        return context;
    }

    public void showLoadingDiaolg() {
        loadingDialog = new MaterialDialog.Builder(context)
                .content("正在加载...").show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void showMessageDialog(String message) {
        messageDialog = new MaterialDialog.Builder(context)
                .title("提示").content(message).show();
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
