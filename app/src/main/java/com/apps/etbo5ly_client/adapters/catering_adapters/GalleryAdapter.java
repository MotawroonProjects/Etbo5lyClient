package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.databinding.GalleryRowBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<KitchenModel.Photo> list;
    private Context context;
    private LayoutInflater inflater;

    public GalleryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GalleryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.gallery_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        RequestOptions options = new RequestOptions();
        Glide.with(context).asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(Tags.base_url+list.get(position).getPhoto())
                .apply(options)
                .into(myHolder.binding.image);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private GalleryRowBinding binding;

        public MyHolder(GalleryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<KitchenModel.Photo> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
