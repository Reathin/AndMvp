package com.rairmmd.mvpdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rairmmd.andmvp.base.BaseFragment;
import com.rairmmd.mvpdemo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }
}
