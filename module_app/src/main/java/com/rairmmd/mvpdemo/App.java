package com.rairmmd.mvpdemo;

import com.rairmmd.andmvp.base.BaseApplication;

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
        initLog(true, "rair");
        initToast(R.color.colorAccent);
        initCrashHandler();
    }
}
