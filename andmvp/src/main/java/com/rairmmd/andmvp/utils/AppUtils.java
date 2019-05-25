package com.rairmmd.andmvp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
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
     * 是否支持指纹
     */
    private boolean isSupportFingerPrint() {
        if (VersionUtils.isM()) {
            FingerprintManager fingerprintManager = (FingerprintManager) AppUtils
                    .getContext().getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null && fingerprintManager.isHardwareDetected()
                    && fingerprintManager.hasEnrolledFingerprints()) {
                return true;
            }
        }
        return false;
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
     * 全局获取color的方法
     *
     * @param colorId 资源ID
     * @return color
     */
    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(context, colorId);
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
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo() {
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
     * @return PackageName
     */
    public static String getAppPackageName() {
        return context.getPackageName();
    }

    /**
     * 获取版本号
     *
     * @return AppVersion
     */
    public static String getAppVersion() {
        return getPackageInfo().versionName;
    }

    /**
     * 震动
     *
     * @param milliseconds 时长
     */
    public static void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
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