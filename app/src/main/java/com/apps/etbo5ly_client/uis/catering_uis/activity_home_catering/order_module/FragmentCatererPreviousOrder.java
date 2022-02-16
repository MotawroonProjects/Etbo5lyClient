package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CatererCurrentOrderAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.CatererPreviousOrderAdapter;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.databinding.BottomSheetDialogBinding;
import com.apps.etbo5ly_client.databinding.BottomSheetRateDialogBinding;
import com.apps.etbo5ly_client.databinding.FragmentCurrentOrderBinding;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCatererCurrentOrderMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCatererPreviousOrderMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class FragmentCatererPreviousOrder extends BaseFragment {
    private HomeActivity activity;
    private FragmentCurrentOrderBinding binding;
    private CatererPreviousOrderAdapter adapter;
    private FragmentCatererPreviousOrderMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static FragmentCatererPreviousOrder newInstance() {
        FragmentCatererPreviousOrder fragment = new FragmentCatererPreviousOrder();
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
        mvvm = ViewModelProviders.of(this).get(FragmentCatererPreviousOrderMvvm.class);
        activityHomeGeneralMvvm.onOrdersRefresh().observe(this, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel());
            }
        });

        activityHomeGeneralMvvm.onUserDateRefresh().observe(this, isRefreshed -> {
            if (isRefreshed) {
                if (adapter != null) {
                    adapter.updateList(new ArrayList<>());
                }
                mvvm.getOrders(getUserModel());
            }
        });

        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.onResendSuccess().observe(activity, success -> {
            if (success) {
                if (adapter != null) {
                    adapter.updateList(new ArrayList<>());
                }
                mvvm.getOrders(getUserModel());
                activityHomeGeneralMvvm.onOrdersRefresh().setValue(true);
            }
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
        binding.recViewLayout.tvNoData.setText(R.string.no_orders);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new CatererPreviousOrderAdapter(activity, this);
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            adapter.updateList(new ArrayList<>());
            binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            mvvm.getOrders(getUserModel());
        });
        mvvm.getOrders(getUserModel());

    }


    public void reOrder(OrderModel orderModel) {
        mvvm.resendOrder(activity, orderModel.getId());
    }


    public void openSheet(OrderModel model) {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        BottomSheetRateDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.bottom_sheet_rate_dialog, null, false);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialogBinding.setModel(model);

        dialogBinding.btnAddRate.setOnClickListener(v -> {
            float rate = dialogBinding.rateBar.getRating();
            String comment = dialogBinding.edtComment.getText().toString();
            dialogBinding.edtComment.setError(null);
            Common.CloseKeyBoard(activity, dialogBinding.edtComment);
            mvvm.rateOrder(activity, getUserModel(), model.getCaterer_id(), model.getId(), rate + "", comment);
            dialog.dismiss();


        });
        dialog.show();


    }


}