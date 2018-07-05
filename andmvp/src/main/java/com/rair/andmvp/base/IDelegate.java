package com.rair.andmvp.base;

import android.view.View;

/**
 * @author Rair
 * @date 2016/12/29
 */

public interface IDelegate {

    /**
     * onResume()
     */
    void resume();

    /**
     * onPause()
     */
    void pause();

    /**
     * onDestory()
     */
    void destory();

    /**
     * onVisible()
     *
     * @param flag 是否可见
     * @param view view
     */
    void visible(boolean flag, View view);

    /**
     * onUnVisible()
     *
     * @param flag 是否可见
     * @param view view
     */
    void gone(boolean flag, View view);

    /**
     * isVisible()
     *
     * @param view view
     */
    void inVisible(View view);

    /**
     * 显示toast
     *
     * @param msg 消息
     */
    void showToast(String msg);
}
