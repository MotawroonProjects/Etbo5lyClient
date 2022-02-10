package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FavoriteRowBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module.FragmentFavorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<KitchenModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public FavoriteAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FavoriteRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.favorite_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(v -> {
            if (fragment instanceof FragmentFavorite){
                FragmentFavorite fragmentFavorite = (FragmentFavorite) fragment;
                fragmentFavorite.navigateToCatererDetails(list.get(myHolder.getAdapterPosition()));
            }
        });

        myHolder.binding.imageFav.setOnClickListener(v -> {
            if (fragment instanceof FragmentFavorite){
                FragmentFavorite fragmentFavorite = (FragmentFavorite) fragment;
                fragmentFavorite.addRemoveFavorite(myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private FavoriteRowBinding binding;

        public MyHolder(FavoriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<KitchenModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
