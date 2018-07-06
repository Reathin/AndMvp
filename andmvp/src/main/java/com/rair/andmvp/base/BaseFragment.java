package com.rair.andmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.rair.andmvp.utils.AppUtils;

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

    protected void translucentStatus(Toolbar toolbar) {
        mImmersionBar.titleBar(toolbar).init();
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
     * 显示toast
     *
     * @param text 提示信息
     */
    private Toast toast;

    public void showToasty(String text) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toasty.info(AppUtils.getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
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
