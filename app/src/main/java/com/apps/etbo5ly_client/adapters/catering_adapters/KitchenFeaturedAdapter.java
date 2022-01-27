package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.KitchenRowBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.home_module.FragmentHomeCatering;

import java.util.List;

public class KitchenFeaturedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<KitchenModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private Fragment fragment;
    public KitchenFeaturedAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        KitchenRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.kitchen_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            if (fragment instanceof FragmentHomeCatering){
                FragmentHomeCatering fragmentHomeCatering = (FragmentHomeCatering) fragment;
                fragmentHomeCatering.setItemKitchen(list.get(myHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private KitchenRowBinding binding;

        public MyHolder(KitchenRowBinding binding) {
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
