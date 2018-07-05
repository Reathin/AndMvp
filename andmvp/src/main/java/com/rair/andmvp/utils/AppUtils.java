package com.rair.andmvp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * <p>Utils初始化相关 </p>
 */
public class AppUtils {

    private static Context context;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context Application上下文
     */
    public static void init(Context context) {
        AppUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * View获取Activity的工具
     *
     * @param view view
     * @return Activity
     */
    @NonNull
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 全局获取String的方法
     *
     * @param id 资源Id
     * @return String
     */
    public static String getString(@StringRes int id) {
        return context.getResources().getString(id);
    }

    /**
     * 获取androidId
     *
     * @return AndroidId
     */
    public static String getAndroidId() {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取App包 信息版本号
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取App包名
     *
     * @param context context
     * @return String
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 震动200毫秒
     */
    public static void vibrate(Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
        if (vibrator == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(200);
        }
    }

    /**
     * 获取异常信息在程序中出错的位置及原因
     */
    public static String getErrorInfo(Throwable throwable) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        throwable.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }

    /**
     * 图片压缩
     *
     * @param file             要压缩的图片文件
     * @param compressListener 结果监听
     */
    public static void compressPicture(File file, OnCompressListener compressListener) {
        Luban.with(AppUtils.getContext()).load(file).ignoreBy(200)
                .setCompressListener(compressListener).launch();
    }

}