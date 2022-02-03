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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CartAdapter;
import com.apps.etbo5ly_client.databinding.FragmentCartBinding;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentCart extends BaseFragment {
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
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
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.onCartRefresh().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                refresh();
            }

        });
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

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(this::refresh);

        binding.flClear.setOnClickListener(v -> {
            manageCartModel.clearCart(activity);
            cartList.clear();
            adapter.updateList(cartList);
            binding.setTotal(manageCartModel.getTotal(getContext()));
            binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

        });

    }

    public void refresh() {
        if (cartList != null && adapter != null) {
            cartList.clear();
            cartList.addAll(manageCartModel.getDishesList(activity));
            binding.recViewLayout.swipeRefresh.setRefreshing(false);
            if (cartList.size() > 0) {
                adapter.updateList(cartList);
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }

            binding.setTotal(manageCartModel.getTotal(getContext()));
        }

    }

    public void updateCart(SendOrderModel.Details model, int adapterPosition) {
        String item_dish_id = "";
        String item_buffet_id = "";
        String item_feasts_id = "";
        String item_offer_id = "";

        if (model.getDishes_id() != null && !model.getDishes_id().isEmpty()) {
            item_dish_id = model.getDishes_id();
        } else if (model.getBuffets_id() != null && !model.getBuffets_id().isEmpty()) {
            item_buffet_id = model.getBuffets_id();
        } else if (model.getFeast_id() != null && !model.getFeast_id().isEmpty()) {
            item_feasts_id = model.getFeast_id();
        } else if (model.getOffer_id() != null && !model.getOffer_id().isEmpty()) {
            item_offer_id = model.getOffer_id();
        }

        SendOrderModel.Details item = new SendOrderModel.Details(item_offer_id, item_dish_id, item_buffet_id, item_feasts_id, model.getCaterer_id(), model.getQty(), model.getImage(), model.getName(), model.getPrice());

        manageCartModel.addItemToCart(activity, item, model.getCaterer_id());
        binding.setTotal(manageCartModel.getTotal(getContext()));

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
