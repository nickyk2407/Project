package com.example.dev4.glowroad.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dev4.glowroad.GlowApplication;
import com.example.dev4.glowroad.R;
import com.example.dev4.glowroad.constant.Constants;
import com.example.dev4.glowroad.di.component.DaggerPhotoSearchComponent;
import com.example.dev4.glowroad.di.component.PhotoSearchComponent;
import com.example.dev4.glowroad.di.modules.ActivityModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

   /* @BindView(R.id.titleTextView)
    protected TextView mHeaderText;*/

    private Unbinder unbinder;
    private PhotoSearchComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component().inject(this);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            Constants.SEARCH_TEXT = "";
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_layout, new FickrPhotosFragment())
                    .commit();
        }
    }

    public PhotoSearchComponent component() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
