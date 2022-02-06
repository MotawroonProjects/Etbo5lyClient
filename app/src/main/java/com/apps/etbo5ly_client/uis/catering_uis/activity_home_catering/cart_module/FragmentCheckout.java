package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.cart_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentCheckoutBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCheckoutMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_zone_cover.ZoneCoverActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentCheckout extends BaseFragment {
    private HomeActivity activity;
    private FragmentCheckoutBinding binding;
    private FragmentCheckoutMvvm mvvm;
    private ManageCartModel manageCartModel;
    private SendOrderModel model;
    private KitchenModel kitchenModel;
    private int req;
    private ActivityResultLauncher<Intent> launcher;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                ZoneCover zoneCover = (ZoneCover) result.getData().getSerializableExtra("data");
                model.setZone(zoneCover.getZone().getTitel());
                model.setZone_id(zoneCover.getZone_id());
                binding.setModel(model);
                calculateTotal();
            }

        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentCheckoutMvvm.class);

        mvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.flLoader.setVisibility(View.VISIBLE);
                binding.llData.setVisibility(View.VISIBLE);

            } else {
                binding.flLoader.setVisibility(View.GONE);

            }
        });

        mvvm.getOnDataSuccess().observe(activity, m -> {
            kitchenModel = m;
            binding.setKitchen(kitchenModel);
            if (kitchenModel != null) {
                binding.llData.setVisibility(View.VISIBLE);
                model.setHasZone(kitchenModel.getZone_cover().size() > 0);
            }

        });

        manageCartModel = ManageCartModel.newInstance();
        model = manageCartModel.getSendOrderModel(activity);
        model.setContext(activity);
        binding.setLang(getLang());
        binding.setModel(model);
        binding.setSubTotal(manageCartModel.getTotal(activity));

        binding.edtCoupon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    binding.btnCheck.setEnabled(false);
                } else {
                    binding.btnCheck.setEnabled(true);

                }
            }
        });

        binding.llZone.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(activity, ZoneCoverActivity.class);
            intent.putExtra("caterer_id", model.getCaterer_id());
            launcher.launch(intent);
        });
    }

    private void calculateTotal() {

    }
}
