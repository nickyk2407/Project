package com.example.dev4.glowroad.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dev4.glowroad.R;
import com.example.dev4.glowroad.adapter.PhotoViewAdapter;
import com.example.dev4.glowroad.apiutil.NetworkState;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.search.Photo;
import com.example.dev4.glowroad.viewmodel.PhotoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FickrPhotosFragment extends Fragment {

    @BindView(R.id.progressbar)
    protected ProgressBar progressbar;

    @BindView(R.id.recyclerview_photos)
    protected RecyclerView rvPhotos;

    @BindView(R.id.swipeContainer)
    protected SwipeRefreshLayout mSwipeContainer;

    private Unbinder unbinder;

    private PhotoViewAdapter mPhotoAdapter;
    private PhotoViewModel mPhotoViewModel;

    public static Fragment getInstance() {
        return new FickrPhotosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flickr_photos_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        hideProgressbar();
        initPhotoView();
    }

    private void showProgressbar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        progressbar.setVisibility(View.GONE);
    }

    private void initPhotoView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPhotos.setLayoutManager(layoutManager);
        mPhotoAdapter = new PhotoViewAdapter(getActivity());

        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        mPhotoViewModel.getPhotoList().observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> photos) {
                mPhotoAdapter.submitList(photos);
            }
        });

        mPhotoViewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mPhotoAdapter.setNetworkState(networkState);
                if (networkState == NetworkState.LOADED) {
                    mSwipeContainer.setRefreshing(false);
                    hideProgressbar();
                    rvPhotos.setVisibility(View.VISIBLE);
                } else if (networkState == NetworkState.LOADING) {
                    if (!mSwipeContainer.isRefreshing()) {
                        showProgressbar();
                    }
                }
            }
        });

        rvPhotos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvPhotos.setAdapter(mPhotoAdapter);

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Constants.API_DEFAULT_PAGE_KEY = 1;
                mPhotoViewModel.retry();
            }
        });

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
