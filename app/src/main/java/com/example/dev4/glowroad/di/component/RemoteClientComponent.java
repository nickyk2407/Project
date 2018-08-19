package com.example.dev4.glowroad.di.component;

import com.example.dev4.glowroad.datamodels.PhotoWebService;
import com.example.dev4.glowroad.di.modules.RemoteClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RemoteClientModule.class)
public interface RemoteClientComponent {
    void inject(PhotoWebService api);
}
