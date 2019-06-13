package com.asv.champions.network;



import android.os.Build;

import com.asv.champions.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prueba.core.gson.GsonUTCDateAdapter;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prueba on 4/3/18.
 */

public class ApiFactory {
/*
    public static AsvService createService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            String lang = Locale.getDefault().getLanguage().toUpperCase();
            Request request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Accept", "application/json")
                                .build();
            return chain.proceed(request);
        });
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(20, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        return retrofit.create(AsvService.class);
    }*/

    public static AsvService createService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(chain -> {
            String lang = Locale.getDefault().getLanguage().toUpperCase();
            Request.Builder builderRequest = chain
                    .request()
                    .newBuilder()
                    .addHeader("Accept", "application/json");

            Request request = builderRequest.build();
            return chain.proceed(request);
        });
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(20, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl( BuildConfig.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        return retrofit.create(AsvService.class);
    }

}
