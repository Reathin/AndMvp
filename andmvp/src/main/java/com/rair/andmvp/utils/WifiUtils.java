package com.rair.andmvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.List;

/**
 * @author AIll.
 * @description 主要功能:Wifi管理工具类
 */
public class WifiUtils {

    private final ConnectivityManager connectivityManager;
    /**
     * 声明Wifi管理对象
     */
    private WifiManager wifiManager;
    /**
     * Wifi信息
     */
    private WifiInfo wifiInfo;
    /**
     * 扫描出来的网络连接列表
     */
    private List<ScanResult> scanResultList;
    /**
     * 网络配置列表
     */
    private List<WifiConfiguration> wifiConfigList;
    /**
     * Wifi锁
     */
    private WifiLock wifiLock;

    private static WifiUtils wifiUtils;

    public static WifiUtils getInstance() {
        if (wifiUtils == null) {
            wifiUtils = new WifiUtils(AppUtils.getContext());
        }
        return wifiUtils;
    }


    /**
     * 构造函数
     *
     * @param context Context
     */
    private WifiUtils(Context context) {
        this.wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.wifiInfo = wifiManager.getConnectionInfo();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取WifiManager
     */
    public WifiManager getWifiManager() {
        return wifiManager;
    }

    /**
     * Wifi状态.
     */
    public boolean isEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * 打开 wifi
     */
    public boolean openWifi() {
        if (!isEnabled()) {
            return wifiManager.setWifiEnabled(true);
        } else {
            return false;
        }
    }

    /**
     * 关闭Wifi
     */
    public boolean closeWifi() {
        if (!isEnabled()) {
            return true;
        } else {
            return wifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 锁定wifi
     * 锁定WiFI就是判断wifi是否建立成功，在这里使用的是held(握手) acquire
     */
    public void lockWifi() {
        wifiLock.acquire();
    }


    /**
     * 解锁wifi
     */
    public void unLockWifi() {
        if (!wifiLock.isHeld()) {
            wifiLock.release(); // 释放资源
        }
    }

    /**
     * 创建一个Wifi锁，需要时调用
     */
    public void createWifiLock() {
        // 创建一个锁的标志
        wifiLock = wifiManager.createWifiLock("flyfly");
    }

    /**
     * 扫描网络
     */
    public void startScan() {
        wifiManager.startScan();
        // 扫描返回结果列表
        scanResultList = wifiManager.getScanResults();
        // 扫描配置列表
        wifiConfigList = wifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> getWifiList() {
        return scanResultList;
    }

    public List<WifiConfiguration> getWifiConfigList() {
        return wifiConfigList;
    }

    /**
     * 获取扫描WIFI列表的信息
     */
    public String lookupScanInfo() {
        StringBuilder scanBuilder = new StringBuilder();
        if (scanResultList == null) {
            return "";
        }
        for (int i = 0; i < scanResultList.size(); i++) {
            ScanResult sResult = scanResultList.get(i);
            scanBuilder.append("编号：" + (i + 1));
            scanBuilder.append(" ");
            scanBuilder.append(sResult.toString());
            scanBuilder.append("\n");
        }
        scanBuilder.append("--------------华丽分割线--------------------");
        for (int i = 0; i < wifiConfigList.size(); i++) {
            scanBuilder.append(wifiConfigList.get(i).toString());
            scanBuilder.append("\n");
        }
        return scanBuilder.toString();
    }

    /**
     * 获取指定Wifi的ssid名称
     *
     * @param netId id
     */
    public String getSSID(int netId) {
        return scanResultList.get(netId).SSID;
    }

    /**
     * 获取指定Wifi的物理地址
     *
     * @param netId id
     */
    public String getBSSID(int netId) {
        return scanResultList.get(netId).BSSID;
    }

    /**
     * 获取指定Wifi的频率
     *
     * @param netId id
     */
    public int getFrequency(int netId) {
        return scanResultList.get(netId).frequency;
    }

    /**
     * 获取指定Wifi的功能
     *
     * @param netId id
     */
    public String getCapabilities(int netId) {
        return scanResultList.get(netId).capabilities;
    }

    /**
     * 获取指定Wifi的信号强度
     *
     * @param netId id
     */
    public int getLevel(int netId) {
        return scanResultList.get(netId).level;
    }


    /**
     * 获取本机Mac地址
     */
    public String getMac() {
        return (wifiInfo == null) ? "" : wifiInfo.getMacAddress();
    }

    public String getBSSID() {
        wifiInfo = wifiManager.getConnectionInfo();
        return (wifiInfo == null) ? null : wifiInfo.getBSSID();
    }

    public String getSSID() {
        return (wifiInfo == null) ? null : wifiInfo.getSSID();
    }

    /**
     * 返回当前连接的网络的ID
     *
     * @return
     */
    public int getCurrentNetId() {
        return (wifiInfo == null) ? null : wifiInfo.getNetworkId();
    }

    /**
     * 返回所有信息
     */
    public WifiInfo getWifiInfo() {
        // 得到连接信息
        wifiInfo = wifiManager.getConnectionInfo();
        return (wifiInfo == null) ? null : wifiInfo;
    }

    /**
     * 获取IP地址
     */
    public int getIP() {
        return (wifiInfo == null) ? null : wifiInfo.getIpAddress();
    }

    /**
     * 添加一个连接
     *
     * @param config 配置
     */
    public boolean addNetWordLink(WifiConfiguration config) {
        int netId = wifiManager.addNetwork(config);
        return wifiManager.enableNetwork(netId, true);
    }

    /**
     * 禁用一个链接
     *
     * @param netId id
     */
    public boolean disableNetWordLink(int netId) {
        wifiManager.disableNetwork(netId);
        return wifiManager.disconnect();
    }

    /**
     * 移除一个链接
     *
     * @param netId id
     */
    public boolean removeNetworkLink(int netId) {
        return wifiManager.removeNetwork(netId);
    }

    /**
     * 不显示SSID
     *
     * @param netId id
     */
    public void hiddenSSID(int netId) {
        wifiConfigList.get(netId).hiddenSSID = true;
    }

    /**
     * 显示SSID
     *
     * @param netId
     */
    public void displaySSID(int netId) {
        wifiConfigList.get(netId).hiddenSSID = false;
    }

    /**
     * 获取当前已连接的wifi名称
     */
    public String getCurrentWifiSsid() {
        //得到连接信息
        wifiInfo = wifiManager.getConnectionInfo();
        String info = wifiInfo.toString();
        String ssid = wifiInfo.getSSID();
        if (info.contains(ssid)) {
            return ssid;
        } else if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            return ssid.substring(1, ssid.length() - 1);
        } else {
            return ssid;
        }
    }

    /**
     * 是否存在有效的WIFI连接
     */
    public boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected();
    }
}
