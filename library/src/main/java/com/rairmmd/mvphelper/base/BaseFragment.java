package com.rairmmd.mvphelper.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.rairmmd.mvphelper.utils.AppUtils;

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
    private View rootView;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        initView();
        return rootView;
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (getP() != null) {
            getP().detachV();
        }
        p = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        unbinder.unbind();
    }

    /**
     * 显示toast
     *
     * @param msg 消息
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
