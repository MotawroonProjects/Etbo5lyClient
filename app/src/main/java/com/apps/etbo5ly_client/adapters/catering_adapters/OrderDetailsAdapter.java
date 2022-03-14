package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.OrderItemRowBinding;
import com.apps.etbo5ly_client.model.OrderModel;

import java.util.List;


public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel.OrderDetail> list;
    private Context context;
    private LayoutInflater inflater;

    public OrderDetailsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_item_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private OrderItemRowBinding binding;

        public MyHolder(OrderItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<OrderModel.OrderDetail> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
