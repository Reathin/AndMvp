package com.rairmmd.mvphelper.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aill on 2017/9/21.
 * RxJava工具类
 */

public class RxUtils {


    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) SCHEDULERS_TRANSFORMER;
    }

}
