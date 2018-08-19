package com.example.dev4.glowroad.datamodels;

import android.content.Context;

import com.example.dev4.glowroad.GlowApplication;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.viewmodel.PhotoViewModel;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoWebService {
    @Inject
    Retrofit retrofit;

    public PhotoWebService(){
        GlowApplication.getApp().getRemoteClientComponent().inject(this);
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
