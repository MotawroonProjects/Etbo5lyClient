package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentOrdersBinding;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
