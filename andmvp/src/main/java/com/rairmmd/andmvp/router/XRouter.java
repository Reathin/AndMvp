package com.rairmmd.andmvp.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity跳转封装
 *
 * @author Rairmmd
 * @date 2017-10-17
 */
public class XRouter {

    private Intent intent;
    private Activity from;
    private Class<?> to;
    private Bundle data;
    private ActivityOptionsCompat options;
    private int requestCode = -1;
    private int enterAnim = RES_NONE;
    private int exitAnim = RES_NONE;

    private static final int RES_NONE = -1;

    private static RouterCallback callback;

    private XRouter() {
        intent = new Intent();
    }

    public static XRouter newIntent(Activity context) {
        XRouter router = new XRouter();
        router.from = context;
        return router;
    }

    public XRouter to(Class<?> to) {
        this.to = to;
        return this;
    }

    public XRouter addFlags(int flags) {
        if (intent != null) {
            intent.addFlags(flags);
        }
        return this;
    }

    public XRouter data(Bundle data) {
        this.data = data;
        return this;
    }

    public XRouter putByte(@Nullable String key, byte value) {
        getBundleData().putByte(key, value);
        return this;
    }

    public XRouter putChar(@Nullable String key, char value) {
        getBundleData().putChar(key, value);
        return this;
    }

    public XRouter putInt(@Nullable String key, int value) {
        getBundleData().putInt(key, value);
        return this;
    }

    public XRouter putLong(@Nullable String key, long value) {
        getBundleData().putLong(key, value);
        return this;
    }

    public XRouter putString(@Nullable String key, String value) {
        getBundleData().putString(key, value);
        return this;
    }

    public XRouter putShort(@Nullable String key, short value) {
        getBundleData().putShort(key, value);
        return this;
    }

    public XRouter putFloat(@Nullable String key, float value) {
        getBundleData().putFloat(key, value);
        return this;
    }

    public XRouter putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        getBundleData().putCharSequence(key, value);
        return this;
    }

    public XRouter putParcelable(@Nullable String key, @Nullable Parcelable value) {
        getBundleData().putParcelable(key, value);
        return this;
    }

    public XRouter putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        getBundleData().putParcelableArray(key, value);
        return this;
    }

    public XRouter putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        getBundleData().putParcelableArrayList(key, value);
        return this;
    }


    public XRouter putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        getBundleData().putIntegerArrayList(key, value);
        return this;
    }

    public XRouter putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        getBundleData().putStringArrayList(key, value);
        return this;
    }

    public XRouter putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        getBundleData().putCharSequenceArrayList(key, value);
        return this;
    }

    public XRouter putSerializable(@Nullable String key, @Nullable Serializable value) {
        getBundleData().putSerializable(key, value);
        return this;
    }

    /**
     * 设置共享元素
     *
     * @param view         共享的view
     * @param shareElement shareName
     * @return XRouter
     */
    public XRouter options(View view, String shareElement) {
        this.options = ActivityOptionsCompat.makeSceneTransitionAnimation(from, view, shareElement);
        return this;
    }

    /**
     * 设置ActivityOptionsCompat 一般用于转场动画或共享元素
     *
     * @param options ActivityOptionsCompat
     * @return XRouter
     */
    public XRouter options(ActivityOptionsCompat options) {
        this.options = options;
        return this;
    }

    /**
     * 设置requestCode
     *
     * @param requestCode requestCode
     */
    public XRouter requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    /**
     * 设置动画
     *
     * @param enterAnim 进入动画
     * @param exitAnim  退出动画
     * @return XRouter
     */
    public XRouter anim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    /**
     * 启动Activity跳转
     */
    public void launch() {
        try {
            if (intent != null && from != null && to != null) {
                if (callback != null) {
                    callback.onBefore(from, to);
                }
                intent.setClass(from, to);
                intent.putExtras(getBundleData());
                if (options == null) {
                    if (requestCode < 0) {
                        from.startActivity(intent);
                    } else {
                        from.startActivityForResult(intent, requestCode);
                    }
                    if (enterAnim > 0 && exitAnim > 0) {
                        from.overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    if (requestCode < 0) {
                        ActivityCompat.startActivity(from, intent, options.toBundle());
                    } else {
                        ActivityCompat.startActivityForResult(from, intent, requestCode, options.toBundle());
                    }
                }
                if (callback != null) {
                    callback.onNext(from, to);
                }
            }
        } catch (Throwable throwable) {
            if (callback != null) {
                callback.onError(from, to, throwable);
            }
        }
    }

    private Bundle getBundleData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }

    /**
     * 结束Activity
     *
     * @param activity activity
     */
    public static void pop(Activity activity) {
        activity.finish();
    }

    /**
     * 设置回调
     *
     * @param callback RouterCallback
     */
    public static void setCallback(RouterCallback callback) {
        XRouter.callback = callback;
    }
}
