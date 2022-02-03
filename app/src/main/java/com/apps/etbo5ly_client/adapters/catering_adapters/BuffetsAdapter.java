package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.BuffetRowBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffets.BuffetsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_feasts.FeastsActivity;

import java.util.List;

public class BuffetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;

    public BuffetsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.buffet_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            if (appCompatActivity instanceof BuffetsActivity){
                BuffetsActivity activity = (BuffetsActivity) appCompatActivity;
                activity.setItemData(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }else if (appCompatActivity instanceof FeastsActivity){
                FeastsActivity activity = (FeastsActivity) appCompatActivity;
                activity.setItemData(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private BuffetRowBinding binding;

        public MyHolder(BuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<BuffetModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
