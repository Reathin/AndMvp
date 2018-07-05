package com.rair.andmvp.base;

import com.socks.library.KLog;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:BasePresent
 */
public class BasePresent<V extends IView> implements IPresent<V> {

    private WeakReference<V> v;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachV(V view) {
        v = new WeakReference<>(view);
    }

    @Override
    public void detachV() {
        disposeAllDisposable();
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    @Override
    public boolean hasV() {
        return false;
    }

    protected V getV() {
        if (v == null || v.get() == null) {
            disposeAllDisposable();
            KLog.w("V不能为空！");
            throw new IllegalStateException("V不能为空！");
        }
        return v.get();
    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    private void disposeAllDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
