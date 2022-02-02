package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.cart_module;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CartAdapter;
import com.apps.etbo5ly_client.databinding.FragmentCartBinding;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends BaseFragment {
    private FragmentCartBinding binding;
    private HomeActivity activity;
    private CartAdapter adapter;
    private ManageCartModel manageCartModel;
    private List<SendOrderModel.Details> cartList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        cartList = new ArrayList<>();
        manageCartModel = ManageCartModel.newInstance();
        cartList.addAll(manageCartModel.getDishesList(activity));
        binding.setTotal(manageCartModel.getTotal(getContext()));
        binding.recViewLayout.tvNoData.setText(R.string.cart_empty);
        adapter = new CartAdapter(activity, this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        if (cartList.size() > 0) {
            binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            adapter.updateList(cartList);

        } else {
            binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

        }

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            cartList.clear();
            cartList.addAll(manageCartModel.getDishesList(activity));
            adapter.updateList(cartList);
            binding.recViewLayout.swipeRefresh.setRefreshing(false);
        });

    }

    public void updateCart(SendOrderModel.Details model, int adapterPosition) {
        manageCartModel.addItemToCart(activity, model,model.getCaterer_id());
    }

    public void delete(SendOrderModel.Details model, int adapterPosition) {
        cartList.remove(adapterPosition);
        String item_id = "";
        if (model.getDishes_id() != null && !model.getDishes_id().isEmpty()) {
            item_id = model.getDishes_id();
        } else if (model.getBuffets_id() != null && !model.getBuffets_id().isEmpty()) {
            item_id = model.getBuffets_id();
        } else if (model.getFeast_id() != null && !model.getFeast_id().isEmpty()) {
            item_id = model.getFeast_id();
        } else if (model.getOffer_id() != null && !model.getOffer_id().isEmpty()) {
            item_id = model.getOffer_id();
        }
        manageCartModel.removeItem(activity, item_id);
        adapter.notifyItemRemoved(adapterPosition);

        if (cartList.size() > 0) {
            binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            adapter.updateList(cartList);

        } else {
            binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

        }

        binding.setTotal(manageCartModel.getTotal(getContext()));


    }
}
