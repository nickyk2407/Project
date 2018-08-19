package com.example.dev4.glowroad.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dev4.glowroad.GlowApplication;
import com.example.dev4.glowroad.R;
import com.example.dev4.glowroad.adapter.PhotoViewAdapter;
import com.example.dev4.glowroad.apiutil.NetworkState;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.datasource.PhotoDataSource;
import com.example.dev4.glowroad.di.component.DaggerPhotoSearchComponent;
import com.example.dev4.glowroad.di.component.PhotoSearchComponent;
import com.example.dev4.glowroad.di.modules.ActivityModule;
import com.example.dev4.glowroad.search.Photo;
import com.example.dev4.glowroad.search.Photos;
import com.example.dev4.glowroad.viewmodel.PhotoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    /*@BindView(R.id.toolbar)
    protected Toolbar toolbar;*/

    private Unbinder unbinder;
    private PhotoSearchComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component().inject(this);
        unbinder = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_layout, new FickrPhotosFragment())
                    .commit();
        }
    }

    public PhotoSearchComponent component(){
        if (component == null) {
            component = DaggerPhotoSearchComponent.builder()
                    .applicationComponent(((GlowApplication) getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return component;
    }

    private void setToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        //toolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
