package com.rairmmd.mvphelper.base;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:
 */
public interface IPresent<V> {

    void attachV(V view);

    void detachV();
}
