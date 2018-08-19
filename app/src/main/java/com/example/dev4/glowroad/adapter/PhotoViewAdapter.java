package com.example.dev4.glowroad.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dev4.glowroad.R;
import com.example.dev4.glowroad.apiutil.NetworkState;
import com.example.dev4.glowroad.search.Photo;
import com.squareup.picasso.Picasso;

public class PhotoViewAdapter extends PagedListAdapter<Photo, RecyclerView.ViewHolder> {
    private NetworkState mNetworkState;

    public PhotoViewAdapter(Context context) {
        super(Photo.DIFF_CALL);
    }

    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_item_list, parent, false);
        return new ViewHolder(v);
    }


    public void setNetworkState(NetworkState networkState) {
        NetworkState prevState = networkState;
        boolean wasLoading = isLoadingData();
        mNetworkState = networkState;
        boolean willLoad = isLoadingData();
        if (wasLoading != willLoad) {
            if (wasLoading) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        }
    }

    public boolean isLoadingData() {
        return (mNetworkState != null && mNetworkState != NetworkState.LOADED);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Photo photo = getItem(position);
        viewHolder.bind(photo);
    }

    public void clearAll() {
        final int size = getItemCount();
        if (size > 0) {
            notifyItemRangeRemoved(0,getItemCount());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImage;
        private final TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mImage = view.findViewById(R.id.display_pic);
            mTitle = view.findViewById(R.id.title);
        }

        public void bind(Photo photo) {
            Picasso picasso = Picasso.with(mContext);
            picasso.load(photo.getUrlS()).into(mImage);
            mTitle.setText(photo.getTitle().trim());
        }
    }
}
