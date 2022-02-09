package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentProfileBinding;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentProfile extends BaseFragment {
    private FragmentProfileBinding binding;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private HomeActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.onUserDateRefresh().observe(this, isRefreshed -> {
            if (isRefreshed) {
                binding.setModel(getUserModel());

            }
        });
        binding.setLang(getLang());
        binding.setModel(getUserModel());
        binding.llOrders.setOnClickListener(v -> {
            activity.displaySpecificPage(3);
        });
    }
}
