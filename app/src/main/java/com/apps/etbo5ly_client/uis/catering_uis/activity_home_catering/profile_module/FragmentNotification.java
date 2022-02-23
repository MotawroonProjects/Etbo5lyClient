package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CatererNotificationAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.FavoriteAdapter;
import com.apps.etbo5ly_client.databinding.FragmentFavoriteBinding;
import com.apps.etbo5ly_client.databinding.FragmentNotificationBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCatererNotificationsMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentFavoriteMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;


public class FragmentNotification extends BaseFragment {
    private FragmentNotificationBinding binding;
    private CatererNotificationAdapter adapter;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentCatererNotificationsMvvm mvvm;
    private HomeActivity activity;
    private ActivityResultLauncher<Intent> launcher;
    private int req;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                activityHomeGeneralMvvm.onFavoriteRefresh().setValue(true);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentCatererNotificationsMvvm.class);
        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);

        });
        mvvm.onDataSuccess().observe(activity, notificationModelList -> {
            if (notificationModelList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }

            if (adapter != null) {
                adapter.updateList(notificationModelList);
            }
        });

        activityHomeGeneralMvvm.onNotificationRefresh().observe(activity, refresh -> {
            if (refresh) {
                mvvm.getNotification(getUserSettings().getOption_id(), getUserModel());

            }
        });


        binding.recViewLayout.tvNoData.setText(R.string.no_notifications_to_show);
        adapter = new CatererNotificationAdapter(activity, this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getNotification(activityHomeGeneralMvvm.getOptionId().getValue(), getUserModel());
        });

        mvvm.getNotification(getUserSettings().getOption_id(), getUserModel());
    }

    public void navigateToCatererDetails(String kitchen_id) {
        req = 1;
        Intent intent = new Intent(activity, KitchenDetailsActivity.class);
        intent.putExtra("kitchen_id", kitchen_id);
        launcher.launch(intent);
    }

}
