package com.example.dev4.glowroad.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.dev4.glowroad.apiutil.NetworkState;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.datamodels.IPhotoSearchServiceApi;
import com.example.dev4.glowroad.datamodels.PhotoWebService;
import com.example.dev4.glowroad.datasource.PhotoDataSource;
import com.example.dev4.glowroad.datasource.PhotoDataSourceFactory;
import com.example.dev4.glowroad.search.Photo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhotoViewModel extends ViewModel {
    private final ExecutorService executor;
    private final MutableLiveData<PhotoDataSource> mDataSource;
    private final LiveData<PagedList<Photo>> mPhotoList;
    private final LiveData<NetworkState> networkStateLiveData;

    public PhotoViewModel() {
        executor = Executors.newFixedThreadPool(5);
        IPhotoSearchServiceApi webService = new PhotoWebService().createService(IPhotoSearchServiceApi.class);
        PhotoDataSourceFactory factory = new PhotoDataSourceFactory(executor, webService);
        mDataSource = factory.getMutableLiveData();
        Constants.API_DEFAULT_PAGE_KEY = 1;

        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), new Function<PhotoDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(PhotoDataSource source) {
                return source.getNetworkState();
            }
        });

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();

        mPhotoList = (new LivePagedListBuilder<Integer, Photo>(factory, pageConfig))
                .setBackgroundThreadExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Photo>> getPhotoList() {
        return mPhotoList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public void retry() {
        mDataSource.getValue().invalidate();
    }
}
