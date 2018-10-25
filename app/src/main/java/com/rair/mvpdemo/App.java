package com.rair.mvpdemo;

import com.rair.andmvp.base.BaseApplication;
import com.rair.andmvp.utils.SPUtils;

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
        SPUtils.init("rair");
    }
}
