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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.FavoriteAdapter;
import com.apps.etbo5ly_client.databinding.FragmentFavoriteBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentFavoriteMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentFavorite extends BaseFragment {
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter adapter;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentFavoriteMvvm mvvm;
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
                mvvm.getFavorite(getUserSettings().getOption_id(), getUserModel());

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentFavoriteMvvm.class);
        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);

        });
        mvvm.onFavoriteSuccess().observe(activity, kitchenModelList -> {
            if (kitchenModelList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }

            if (adapter != null) {
                adapter.updateList(kitchenModelList);
            }
        });
        mvvm.onAddRemoveFavoriteSuccess().observe(activity, itemPos -> {

            adapter.notifyItemRemoved(itemPos);


            if (mvvm.onFavoriteSuccess().getValue() != null && mvvm.onFavoriteSuccess().getValue().size() == 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

            }
            activityHomeGeneralMvvm.onFavoriteRefresh().setValue(true);
        });
        activityHomeGeneralMvvm.getOptionId().observe(activity, option_id -> {
            mvvm.getFavorite(option_id, getUserModel());
        });

        binding.recViewLayout.tvNoData.setText(R.string.no_fav);
        adapter = new FavoriteAdapter(activity, this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getFavorite(activityHomeGeneralMvvm.getOptionId().getValue(), getUserModel());
        });

        mvvm.getFavorite(getUserSettings().getOption_id(), getUserModel());
    }

    public void navigateToCatererDetails(KitchenModel kitchenModel) {
        req = 1;
        Intent intent = new Intent(activity, KitchenDetailsActivity.class);
        intent.putExtra("kitchen_id", kitchenModel.getId());
        launcher.launch(intent);
    }

    public void addRemoveFavorite(int adapterPosition) {
        mvvm.addRemoveFavorite(getUserModel(), adapterPosition);
    }


}