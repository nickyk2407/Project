package com.example.dev4.glowroad.di.modules;

import com.example.dev4.glowroad.di.scopes.ActivityScope;
import com.example.dev4.glowroad.viewmodel.PhotoViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoSearchModule {
    @Provides
    @ActivityScope
    PhotoViewModel providePhotoSearchViewModel(PhotoViewModel viewModel){
        return viewModel;
    }
}
