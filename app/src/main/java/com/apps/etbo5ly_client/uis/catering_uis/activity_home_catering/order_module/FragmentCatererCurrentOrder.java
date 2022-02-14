package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CatererCurrentOrderAdapter;
import com.apps.etbo5ly_client.databinding.FragmentCurrentOrderBinding;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCatererCurrentOrderMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;


public class FragmentCatererCurrentOrder extends BaseFragment {
    private HomeActivity activity;
    private FragmentCurrentOrderBinding binding;
    private CatererCurrentOrderAdapter adapter;
    private FragmentCatererCurrentOrderMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static FragmentCatererCurrentOrder newInstance() {
        FragmentCatererCurrentOrder fragment = new FragmentCatererCurrentOrder();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentCatererCurrentOrderMvvm.class);
        activityHomeGeneralMvvm.onOrdersRefresh().observe(this, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel());
            }
        });
        activityHomeGeneralMvvm.onUserDateRefresh().observe(this, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel());
            }
        });

        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.onDataSuccess().observe(activity, orderList -> {
            if (orderList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);


            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
            if (adapter != null) {
                adapter.updateList(orderList);
            }
        });
        mvvm.onResendSuccess().observe(activity, success -> {
           if (success){
               activityHomeGeneralMvvm.onOrdersRefresh();
           }
        });

        binding.recViewLayout.tvNoData.setText(R.string.no_orders);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new CatererCurrentOrderAdapter(activity, this);
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrders(getUserModel()));
        mvvm.getOrders(getUserModel());
    }

    public void navigateToFragmentOrderDetails(View v, String order_id) {
        Bundle bundle = new Bundle();
        bundle.putString("order_id", order_id);
        Navigation.findNavController(v).navigate(R.id.fragmentOrderDetails, bundle);
    }


    public void resendOrder(String id) {
        mvvm.resendOrder(activity, id);
    }
}