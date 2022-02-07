package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.ZoneRowBinding;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.uis.catering_uis.activity_zone_cover.ZoneCoverActivity;

import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ZoneCover> list;
    private LayoutInflater inflater;
    private AppCompatActivity activity;
    private String lang;

    public ZoneAdapter(Context context, String lang) {
        inflater = LayoutInflater.from(context);
        activity = (AppCompatActivity) context;
        this.lang = lang;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ZoneRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.zone_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
        myHolder.itemView.setOnClickListener(v -> {
            if (activity instanceof ZoneCoverActivity) {
                ZoneCoverActivity zoneCoverActivity = (ZoneCoverActivity) activity;
                zoneCoverActivity.setItemZone(list.get(myHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ZoneRowBinding binding;

        public MyHolder(ZoneRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<ZoneCover> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
