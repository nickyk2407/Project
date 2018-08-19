package com.example.dev4.glowroad.di.component;

import android.content.Context;

import com.example.dev4.glowroad.GlowApplication;
import com.example.dev4.glowroad.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GlowApplication app);
    void inject(Context context);
}
