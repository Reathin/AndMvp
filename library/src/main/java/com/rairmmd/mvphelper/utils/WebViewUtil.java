package com.rairmmd.mvphelper.utils;

/**
 * @Description:主要功能:WebView管理器，提供常用设置
 */

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtil {
    private WebView webView;
    private WebSettings webSettings;

    public WebViewUtil(WebView webView){
        this.webView = webView;
        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
    }

    /**
     * 开启自适应功能
     */
    public WebViewUtil enableAdaptive(){
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 禁用自适应功能
     */
    public WebViewUtil disableAdaptive(){
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 开启缩放功能
     */
    public WebViewUtil enableZoom(){
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        return this;
    }

    /**
     * 禁用缩放功能
     */
    public WebViewUtil disableZoom(){
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setBuiltInZoomControls(false);
        return this;
    }

    /**
     * 开启JavaScript
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewUtil enableJavaScript(){
        webSettings.setJavaScriptEnabled(true);
        return this;
    }

    /**
     * 禁用JavaScript
     */
    public WebViewUtil disableJavaScript(){
        webSettings.setJavaScriptEnabled(false);
        return this;
    }

    /**
     * 开启JavaScript自动弹窗
     */
    public WebViewUtil enableJavaScriptOpenWindowsAutomatically(){
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        return this;
    }

    /**
     * 禁用JavaScript自动弹窗
     */
    public WebViewUtil disableJavaScriptOpenWindowsAutomatically(){
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        return this;
    }

    /**
     * 返回
     * @return true：已经返回，false：到头了没法返回了
     */
    public boolean goBack(){
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }else{
            return false;
        }
    }
}
