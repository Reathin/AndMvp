package com.rairmmd.mvphelper.net;

import android.text.TextUtils;

import com.rairmmd.mvphelper.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Rair
 * @date 2017/10/25
 * <p>
 * desc:单例
 */

public class RApiServer {

    private static RApiServer instance;

    private RApiServer() {
    }

    public static RApiServer getInstance() {
        if (instance == null) {
            synchronized (RApiServer.class) {
                if (instance == null) {
                    instance = new RApiServer();
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
    public Retrofit createRetrofit(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl 不能为空");
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient().build())
                .addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }


    public OkHttpClient.Builder getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(Config.CONNECTION_POOL,
                Config.CONNECTION_KEEPALIVE, TimeUnit.MINUTES));
        builder.connectTimeout(Config.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }
}
