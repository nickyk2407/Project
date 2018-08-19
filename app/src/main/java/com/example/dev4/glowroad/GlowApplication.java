package com.example.dev4.glowroad;

import android.app.Application;

public class GlowApplication extends Application {
    private static GlowApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app= this;
    }

    public static GlowApplication getApp() {
        return app;
    }
}
