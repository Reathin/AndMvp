package com.rairmmd.andmvp.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Rair
 * @date 2017/9/21
 * RxJava工具类 线程调度
 */
public class RxUtils {

    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable observable) {
            return observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> ObservableTransformer<T, T> apply() {
        return (ObservableTransformer<T, T>) SCHEDULERS_TRANSFORMER;
    }
}
