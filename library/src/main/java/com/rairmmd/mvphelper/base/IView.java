package com.rairmmd.mvphelper.base;

import android.view.View;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:IView
 */
public interface IView<P> {

    void bindUI(View rootView);

    void initView();

    void initData();

    int getLayoutId();

    P newP();
}