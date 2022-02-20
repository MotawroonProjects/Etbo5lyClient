package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.AddressRowBinding;
import com.apps.etbo5ly_client.model.AddressModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_address.AddressActivity;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddressModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private AddressActivity activity;

    public AddressAdapter(Context context,String lang) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
        activity = (AddressActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AddressRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.address_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
        myHolder.itemView.setOnClickListener(v -> {
            activity.setItemData(list.get(myHolder.getAdapterPosition()));

        });

        myHolder.binding.llDelete.setOnClickListener(v -> {
            activity.deleteAddress(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());

        });


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddressRowBinding binding;

        public MyHolder(AddressRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<AddressModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
