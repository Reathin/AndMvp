package com.rairmmd.andmvp.http;

import android.text.TextUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:单例XApi
 */

public class XApi {

    private static XApi instance;
    private int connectPoolSize;
    private int connectTimeout;
    private int connectKeepalive;
    private int connectReadTimeout;
    private Interceptor interceptor;
    private Https.SSLParams socketFactory;
    private boolean hostNameVerifier;

    private XApi() {
        connectPoolSize = 5;
        connectReadTimeout = 10;
        connectKeepalive = 30;
        connectTimeout = 10;
    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }

    /**
     * 创建请求
     *
     * @param baseUrl baseUrl
     * @param service 接口请求
     */
    public static <S> S create(String baseUrl, Class<S> service) {
        return getInstance().createRetrofit(baseUrl).create(service);
    }

    /**
     * 构建Retrofit
     *
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    private Retrofit createRetrofit(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl 不能为空");
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl).client(getClient().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    /**
     * 构建OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient.Builder getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(connectPoolSize, connectKeepalive, TimeUnit.SECONDS));
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        builder.readTimeout(connectReadTimeout, TimeUnit.SECONDS);
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        if (socketFactory != null) {
            builder.sslSocketFactory(socketFactory.sSLSocketFactory, socketFactory.trustManager);
        }
        if (hostNameVerifier) {
            builder.hostnameVerifier(new Https.UnSafeHostnameVerifier());
        }
        return builder;
    }

    public XApi setConnectKeepalive(int connectKeepalive) {
        this.connectKeepalive = connectKeepalive;
        return this;
    }

    public XApi setConnectPoolSize(int connectPoolSize) {
        this.connectPoolSize = connectPoolSize;
        return this;
    }

    public XApi setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public XApi setReadTimeout(int connectReadTimeout) {
        this.connectReadTimeout = connectReadTimeout;
        return this;
    }

    public XApi setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public XApi setCertificate(InputStream certificateFile, InputStream bksFile, String password) {
        socketFactory = Https.getSslSocketFactory(certificateFile, bksFile, password);
        return this;
    }

    /**
     * 忽略证书认证
     *
     * @param hostNameVerifier 是否忽略
     */
    public XApi setHostNameVerifier(boolean hostNameVerifier) {
        this.hostNameVerifier = hostNameVerifier;
        return this;
    }
}
