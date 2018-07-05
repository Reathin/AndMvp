package com.rair.mvpdemo;

import com.rair.andmvp.base.BaseApplication;
import com.socks.library.KLog;

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
    }
}
