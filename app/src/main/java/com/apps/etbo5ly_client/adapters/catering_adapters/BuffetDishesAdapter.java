package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.DishBuffetRowBinding;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_details.BuffetDetailsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_dishes.DishesActivity;

import java.util.List;

public class BuffetDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DishModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;

    public BuffetDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DishBuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.dish_buffet_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.imgIncrease.setOnClickListener(v -> {
            DishModel model = list.get(myHolder.getAdapterPosition());
            int amount = model.getAmount();
            amount++;
            model.setAmount(amount);
            list.set(myHolder.getAdapterPosition(), model);
            myHolder.binding.setModel(model);

            if (appCompatActivity instanceof DishesActivity) {
                DishesActivity activity = (DishesActivity) appCompatActivity;
                activity.updateCart(model, myHolder.getAdapterPosition());
            }
        });

        myHolder.binding.imgDecrease.setOnClickListener(v -> {
            DishModel model = list.get(myHolder.getAdapterPosition());
            int amount = model.getAmount();
            if (amount > 0) {
                amount--;
                model.setAmount(amount);
                list.set(myHolder.getAdapterPosition(), model);
                myHolder.binding.setModel(model);

            }


            if (appCompatActivity instanceof DishesActivity) {
                DishesActivity activity = (DishesActivity) appCompatActivity;
                activity.updateCart(model, myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private DishBuffetRowBinding binding;

        public MyHolder(DishBuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<DishModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
