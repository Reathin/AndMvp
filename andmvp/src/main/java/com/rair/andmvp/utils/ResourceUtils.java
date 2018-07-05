package com.rair.andmvp.utils;

import android.support.v4.content.ContextCompat;

/**
 * @author Rair
 * @date 2018/7/2
 * <p>
 * desc:
 */
public class ResourceUtils {

    public static int getColor(int colorId) {
        return ContextCompat.getColor(AppUtils.getContext(), colorId);
    }
}
