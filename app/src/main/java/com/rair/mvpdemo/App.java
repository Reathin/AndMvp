package com.rair.mvpdemo;

import android.support.v4.content.ContextCompat;

import com.rair.andmvp.base.BaseApplication;
import com.socks.library.KLog;

import es.dmoral.toasty.Toasty;

/**
 * @author Rair
 * @date 2018/7/5
 * <p>
 * desc:
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG, "Rair");
        Toasty.Config.getInstance()
                .setInfoColor(ContextCompat.getColor(this, R.color.colorAccent))
                .apply();
    }
}
