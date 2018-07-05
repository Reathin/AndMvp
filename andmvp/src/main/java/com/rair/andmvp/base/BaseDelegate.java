package com.rair.andmvp.base;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wanglei on 2016/12/29.
 */

public class BaseDelegate implements IDelegate {

    private Context context;

    private BaseDelegate(Context context) {
        this.context = context;
    }

    public static IDelegate create(Context context) {
        return new BaseDelegate(context);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void visible(boolean flag, View view) {
        if (flag) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void gone(boolean flag, View view) {
        if (flag) {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void inVisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
