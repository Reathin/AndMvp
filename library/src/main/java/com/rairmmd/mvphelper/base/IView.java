package com.rairmmd.mvphelper.base;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:
 */
public interface IView<P> {

    void getExtraData();

    void initView();

    void initData();

    int getLayoutId();

    P newP();
}
