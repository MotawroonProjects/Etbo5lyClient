package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.offer_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.OfferAdapter;
import com.apps.etbo5ly_client.databinding.FragmentOfferBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.OfferModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentOfferMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentOffers extends BaseFragment {
    private FragmentOfferBinding binding;
    private OfferAdapter adapter;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentOfferMvvm mvvm;
    private HomeActivity activity;
    private ActivityResultLauncher<Intent> launcher;
    private int req;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                activityHomeGeneralMvvm.setOnRefreshSuccess(true);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offer, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentOfferMvvm.class);
        mvvm.getIsOfferLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);

        });
        mvvm.onOfferDataSuccess().observe(activity, offerList -> {
            if (offerList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
                if (adapter != null) {
                    adapter.updateList(offerList);
                }
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
        });

        activityHomeGeneralMvvm.getOptionId().observe(activity, option_id -> {
            mvvm.getOfferData(option_id);
        });

        adapter = new OfferAdapter(activity, this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getOfferData(activityHomeGeneralMvvm.getOptionId().getValue());
        });
    }

    public void setItemOffer(KitchenModel kitchenModel) {
        req = 1;
        Intent intent = new Intent(activity, KitchenDetailsActivity.class);
        intent.putExtra("kitchen_id", kitchenModel.getId());
        launcher.launch(intent);
    }
}
