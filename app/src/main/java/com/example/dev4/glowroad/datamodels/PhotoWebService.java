package com.example.dev4.glowroad.datamodels;

import android.content.Context;

import com.example.dev4.glowroad.GlowApplication;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.viewmodel.PhotoViewModel;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoWebService {

    public static <S> S createService(Class<S> serviceClass) {
        provideOkHttpClient(provideOkHttpCache());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private static Cache provideOkHttpCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(GlowApplication.getApp().getCacheDir(), cacheSize);
        return cache;
    }

    private static OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

}
