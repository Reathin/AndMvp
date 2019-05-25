package com.rairmmd.andmvp.base;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:
 */
public interface IPresent<V> {

    /**
     * attachV
     *
     * @param view view
     */
    void attachV(V view);

    /**
     * detachV
     */
    void detachV();

    /**
     * 是否有v
     *
     * @return Boolean
     */
    boolean hasV();
}
