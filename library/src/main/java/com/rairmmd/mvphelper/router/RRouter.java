package com.rairmmd.mvphelper.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:
 */
public class RRouter {

    private Intent intent;
    private Activity from;
    private Class<?> to;
    private Bundle data;
    private ActivityOptionsCompat options;
    private int requestCode = -1;
    private int enterAnim = RES_NONE;
    private int exitAnim = RES_NONE;

    public static final int RES_NONE = -1;

    private RRouter() {
        intent = new Intent();
    }

    public static RRouter newIntent(Activity context) {
        RRouter router = new RRouter();
        router.from = context;
        return router;
    }

    public RRouter to(Class<?> to) {
        this.to = to;
        return this;
    }

    public RRouter addFlags(int flags) {
        if (intent != null) {
            intent.addFlags(flags);
        }
        return this;
    }

    public RRouter data(Bundle data) {
        this.data = data;
        return this;
    }

    public RRouter putByte(@Nullable String key, byte value) {
        getBundleData().putByte(key, value);
        return this;
    }

    public RRouter putChar(@Nullable String key, char value) {
        getBundleData().putChar(key, value);
        return this;
    }

    public RRouter putInt(@Nullable String key, int value) {
        getBundleData().putInt(key, value);
        return this;
    }

    public RRouter putString(@Nullable String key, String value) {
        getBundleData().putString(key, value);
        return this;
    }

    public RRouter putShort(@Nullable String key, short value) {
        getBundleData().putShort(key, value);
        return this;
    }

    public RRouter putFloat(@Nullable String key, float value) {
        getBundleData().putFloat(key, value);
        return this;
    }

    public RRouter putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        getBundleData().putCharSequence(key, value);
        return this;
    }

    public RRouter putParcelable(@Nullable String key, @Nullable Parcelable value) {
        getBundleData().putParcelable(key, value);
        return this;
    }

    public RRouter putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        getBundleData().putParcelableArray(key, value);
        return this;
    }

    public RRouter putParcelableArrayList(@Nullable String key,
                                          @Nullable ArrayList<? extends Parcelable> value) {
        getBundleData().putParcelableArrayList(key, value);
        return this;
    }


    public RRouter putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        getBundleData().putIntegerArrayList(key, value);
        return this;
    }

    public RRouter putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        getBundleData().putStringArrayList(key, value);
        return this;
    }

    public RRouter putCharSequenceArrayList(@Nullable String key,
                                            @Nullable ArrayList<CharSequence> value) {
        getBundleData().putCharSequenceArrayList(key, value);
        return this;
    }

    public RRouter putSerializable(@Nullable String key, @Nullable Serializable value) {
        getBundleData().putSerializable(key, value);
        return this;
    }


    public RRouter options(ActivityOptionsCompat options) {
        this.options = options;
        return this;
    }

    public RRouter requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public RRouter anim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public void launch() {
        try {
            if (intent != null && from != null && to != null) {
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
            }
        } catch (Throwable throwable) {
        }
    }

    private Bundle getBundleData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }

    public static void pop(Activity activity) {
        activity.finish();
    }

}
