package com.example.dev4.glowroad.di.component;

import android.app.Activity;

import com.example.dev4.glowroad.activities.MainActivity;
import com.example.dev4.glowroad.di.modules.ActivityModule;
import com.example.dev4.glowroad.di.modules.PhotoSearchModule;
import com.example.dev4.glowroad.di.scopes.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PhotoSearchModule.class})
public interface PhotoSearchComponent {
    Activity activityContext();

    void inject(MainActivity activity);
}
