package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.CatererCurrentOrderRowBinding;
import com.apps.etbo5ly_client.databinding.CatererPreviousOrderRowBinding;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module.FragmentCatererCurrentOrder;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module.FragmentCatererPreviousOrder;

import java.util.List;

public class CatererPreviousOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel> list;
    private LayoutInflater inflater;
    private Fragment fragment;

    public CatererPreviousOrderAdapter(Context context, Fragment fragment) {
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CatererPreviousOrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.caterer_previous_order_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.btnReOrder.setOnClickListener(v -> {
            if (fragment instanceof FragmentCatererPreviousOrder){
                FragmentCatererPreviousOrder fragmentCatererPreviousOrder = (FragmentCatererPreviousOrder) fragment;
                fragmentCatererPreviousOrder.reOrder(list.get(myHolder.getAdapterPosition()));
            }
        });

        myHolder.binding.btnRate.setOnClickListener(v -> {
            if (fragment instanceof FragmentCatererPreviousOrder){
                FragmentCatererPreviousOrder fragmentCatererPreviousOrder = (FragmentCatererPreviousOrder) fragment;
                fragmentCatererPreviousOrder.openSheet(list.get(myHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CatererPreviousOrderRowBinding binding;

        public MyHolder(CatererPreviousOrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<OrderModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
