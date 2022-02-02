package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.CartRowBinding;
import com.apps.etbo5ly_client.databinding.DishBuffetRowBinding;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_dishes.DishesActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.cart_module.FragmentCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SendOrderModel.Details> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public CartAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CartRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.imageDelete.setOnClickListener(v -> {
            SendOrderModel.Details model = list.get(myHolder.getAdapterPosition());

            if (fragment instanceof FragmentCart) {
                FragmentCart fragmentCart = (FragmentCart) fragment;
                fragmentCart.delete(model, myHolder.getAdapterPosition());
            }
        });
        myHolder.binding.imgIncrease.setOnClickListener(v -> {
            SendOrderModel.Details model = list.get(myHolder.getAdapterPosition());
            int amount = Integer.parseInt(model.getQty());
            amount++;
            model.setQty(amount + "");
            list.set(myHolder.getAdapterPosition(), model);
            myHolder.binding.setModel(model);

            if (fragment instanceof FragmentCart) {
                FragmentCart fragmentCart = (FragmentCart) fragment;
                fragmentCart.updateCart(model, myHolder.getAdapterPosition());
            }

        });

        myHolder.binding.imgDecrease.setOnClickListener(v -> {
            SendOrderModel.Details model = list.get(myHolder.getAdapterPosition());
            int amount = Integer.parseInt(model.getQty());
            if (amount > 0) {
                amount--;
                model.setQty(amount + "");
                list.set(myHolder.getAdapterPosition(), model);
                myHolder.binding.setModel(model);

            }


            if (fragment instanceof FragmentCart) {
                FragmentCart fragmentCart = (FragmentCart) fragment;
                fragmentCart.updateCart(model, myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CartRowBinding binding;

        public MyHolder(CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<SendOrderModel.Details> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
