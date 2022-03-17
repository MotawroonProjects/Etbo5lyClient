package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentProfileBinding;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_app_category.AppCategoryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;
import com.apps.etbo5ly_client.uis.common_uis.activity_sign_up.SignUpActivity;

public class FragmentProfile extends BaseFragment {
    private FragmentProfileBinding binding;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private HomeActivity activity;
    private int req;
    private ActivityResultLauncher<Intent> launcher;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                UserModel userModel = getUserModel();
                binding.setModel(userModel);
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                activityHomeGeneralMvvm.getOptionId().setValue(getUserSettings().getOption_id());
                activityHomeGeneralMvvm.getOnServiceChanged().setValue(true);
            }
        });

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

        activityHomeGeneralMvvm.getDisplayFragmentNotification().observe(activity, display -> {
            if (display) {
                new Handler().postDelayed(() -> Navigation.findNavController(binding.getRoot()).navigate(R.id.fragmentNotification),2000);

            }
        });

        binding.llOrders.setOnClickListener(v -> {
            activity.displaySpecificPage(3);
        });

        binding.llFavourite.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.fragmentFavorite);
        });
        binding.llNotifications.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.fragmentNotification);
        });

        binding.llSetting.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.fragmentCatrerSetting);
        });

        binding.llContactUs.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.fragmentContactUs);
        });

        binding.llUpdateProfile.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(activity, SignUpActivity.class);
            launcher.launch(intent);

        });

        binding.llService.setOnClickListener(v -> {
            req = 2;
            Intent intent = new Intent(activity, AppCategoryActivity.class);
            intent.putExtra("action", "profile");
            launcher.launch(intent);
        });

        binding.llLogout.setOnClickListener(v -> {
            activity.logout();
        });
    }
}
