package com.rairmmd.andmvp.loader;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.rairmmd.andmvp.utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;

/**
 * @author Rair
 * @date 2018/10/25
 * <p>
 * desc:
 */
public class ImageLoader {

    /**
     * 初始化相册选择
     */
    public static void initAlbum() {
        Album.initialize(AlbumConfig.newBuilder(AppUtils.getContext())
                .setLocale(Locale.CHINA).setAlbumLoader(new MediaLoader()).build());
    }

    /**
     * 加载图片
     *
     * @param url    URL
     * @param target ImageView
     */
    public static void load(String url, ImageView target) {
        Picasso.get().load(url).into(target);
    }

    /**
     * 加载图片
     *
     * @param url         URL
     * @param placeholder 占位图
     * @param target      ImageView
     */
    public static void load(String url, @DrawableRes int placeholder, ImageView target) {
        Picasso.get().load(url).placeholder(placeholder).into(target);
    }

    /**
     * 加载图片
     *
     * @param url         URL
     * @param placeholder 占位图
     * @param target      ImageView
     */
    public static void load(String url, Drawable placeholder, ImageView target) {
        Picasso.get().load(url).placeholder(placeholder).into(target);
    }

    /**
     * 加载图片
     *
     * @param resId  资源id
     * @param target ImageView
     */
    public static void load(@DrawableRes int resId, ImageView target) {
        Picasso.get().load(resId).into(target);
    }
}
