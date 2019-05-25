package com.rairmmd.andmvp.base;

import java.lang.ref.WeakReference;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:BasePresent
 */
public class BasePresent<V extends IView> implements IPresent<V> {

    private WeakReference<V> v;

    @Override
    public void attachV(V view) {
        v = new WeakReference<>(view);
    }

    @Override
    public void detachV() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    @Override
    public boolean hasV() {
        return v != null && v.get() != null;
    }

    protected V getV() {
        if (v == null || v.get() == null) {
            throw new IllegalStateException("V不能为空！");
        }
        return v.get();
    }
}
