package com.example.dev4.glowroad.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.dev4.glowroad.apiutil.NetworkState;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.datamodels.IPhotoSearchServiceApi;
import com.example.dev4.glowroad.search.Photo;
import com.example.dev4.glowroad.search.SearchResponse;

import java.util.concurrent.ExecutorService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dev4.glowroad.constant.Constants.API_DEFAULT_PAGE_KEY;

public class PhotoDataSource extends PageKeyedDataSource<Integer, Photo> {
    private final IPhotoSearchServiceApi mWebService;
    private final ExecutorService retryExecutor;
    private MutableLiveData<NetworkState> initialLoading;
    private MutableLiveData<NetworkState> networkState;

    public PhotoDataSource(ExecutorService executor, IPhotoSearchServiceApi webService) {
        this.mWebService = webService;
        this.initialLoading = new MutableLiveData<>();
        this.networkState = new MutableLiveData<>();
        this.retryExecutor = executor;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Photo> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(SearchResponse response) {
                if (response.getStat() != null && response.getStat().equalsIgnoreCase("ok") &&
                        response.getPhotos() != null && !response.getPhotos().getPhoto().isEmpty()) {
                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    API_DEFAULT_PAGE_KEY++;
                    callback.onResult(response.getPhotos().getPhoto(), null, API_DEFAULT_PAGE_KEY);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.getMessage()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                String errorMessage = e.getMessage();
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }

            @Override
            public void onComplete() {

            }
        };

        mWebService.search(Constants.API_SEARCH_METHOD, Constants.API_KEY, Constants.API_RESPONSE_FORMAT, Constants.API_SEARCH_NOJSONCALLBACK,
                Constants.SEARCH_TEXT_SHIRTS, Constants.API_SEARCH_EXTRAS, Constants.API_SEARCH_PER_PAGE_COUNT, API_DEFAULT_PAGE_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Photo> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(SearchResponse response) {

                if (response.getStat() != null && response.getStat().equalsIgnoreCase("ok") &&
                        response.getPhotos() != null && !response.getPhotos().getPhoto().isEmpty()) {
                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    //int nextKey = (params.key == response.getPhotos().getPage()) ? (params.key + 1) : API_DEFAULT_PAGE_KEY;
                    API_DEFAULT_PAGE_KEY = (params.key + 1);
                    callback.onResult(response.getPhotos().getPhoto(), API_DEFAULT_PAGE_KEY);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.getMessage()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.getMessage()));
                }
            }

            @Override
            public void onError(Throwable e) {
                String errorMessage = e.getMessage();
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }

            @Override
            public void onComplete() {

            }
        };

        mWebService.search(Constants.API_SEARCH_METHOD, Constants.API_KEY, Constants.API_RESPONSE_FORMAT, Constants.API_SEARCH_NOJSONCALLBACK,
                Constants.SEARCH_TEXT_SHIRTS, Constants.API_SEARCH_EXTRAS, Constants.API_SEARCH_PER_PAGE_COUNT, API_DEFAULT_PAGE_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void addInvalidatedCallback(@NonNull InvalidatedCallback onInvalidatedCallback) {
        super.addInvalidatedCallback(onInvalidatedCallback);
    }
}
