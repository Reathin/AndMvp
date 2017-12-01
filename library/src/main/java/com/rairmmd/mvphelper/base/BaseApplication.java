package com.rairmmd.mvphelper.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.rairmmd.mvphelper.BuildConfig;
import com.rairmmd.mvphelper.utils.AppUtil;
import com.rairmmd.mvphelper.utils.SPUtil;
import com.socks.library.KLog;

import java.util.Stack;

import es.dmoral.toasty.Toasty;

/**
 * Created by Rair on 2017/12/1.
 * Email:rairmmd@gmail.com
 * Author:Rair
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;
    private Stack<Activity> activityStack;
    protected boolean isDebug;

    public static BaseApplication getIns() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppUtil.init(this);
        SPUtil.getInstance();
        //初始化log日志
        KLog.init(BuildConfig.LOG_DEBUG, "Rair");
        //初始化Toasty
        Toasty.Config.getInstance().setInfoColor(Color.parseColor("#a9dbfe"))
                .setSuccessColor(Color.parseColor("#a9dbfe")).apply();
    }

    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            //杀死后台进程需要在AndroidManifest中声明android.permission.KILL_BACKGROUND_PROCESSES；
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                activityManager.killBackgroundProcesses(context.getPackageName());
                System.exit(0);
            }
        } catch (Exception e) {
            KLog.e("app exit" + e.getMessage());
        }
    }
}
