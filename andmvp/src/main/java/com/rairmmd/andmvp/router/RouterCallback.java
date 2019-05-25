package com.rairmmd.andmvp.router;

import android.app.Activity;

/**
 * @author wanglei
 * @date 2016/11/29
 */

public interface RouterCallback {

    /**
     * 跳转前
     *
     * @param from Activity A
     * @param to   Activity B class
     */
    void onBefore(Activity from, Class<?> to);

    /**
     * 跳转后
     *
     * @param from Activity A
     * @param to   Activity B class
     */
    void onNext(Activity from, Class<?> to);

    /**
     * 跳转异常
     *
     * @param from      Activity A
     * @param to        Activity B class
     * @param throwable 异常信息
     */
    void onError(Activity from, Class<?> to, Throwable throwable);

}
