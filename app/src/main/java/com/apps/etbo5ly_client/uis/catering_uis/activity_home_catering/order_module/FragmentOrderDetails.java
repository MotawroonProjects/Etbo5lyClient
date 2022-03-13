package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.databinding.FragmentOrderDetailsBinding;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCatererCurrentOrderMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentOrderDetailsMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class FragmentOrderDetails extends BaseFragment {
    private HomeActivity activity;
    private FragmentOrderDetailsBinding binding;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentOrderDetailsMvvm mvvm;
    private String order_id = "";
    private OrderModel orderModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            order_id = bundle.getString("order_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentOrderDetailsMvvm.class);
        mvvm.getIsDataLoading().observe(activity, isLoading -> {
            if (!isLoading) {
                binding.flLoader.setVisibility(View.GONE);
            }
        });

        mvvm.onDataSuccess().observe(activity, orderModel -> {
            this.orderModel = orderModel;
            binding.setModel(orderModel);
            updateUi();
        });

        activityHomeGeneralMvvm.onOrderRefresh().observe(activity, order -> {
            if (order == null) {
                //maybe sent from home activity from notifications come from eventbus order will be null
                activityHomeGeneralMvvm.onOrdersRefresh().setValue(true);
                mvvm.getOrders(order_id);
            }
        });

        activityHomeGeneralMvvm.getOnFragmentOrderDetailsRefresh().observe(activity, refresh -> {
            if (refresh) {
                mvvm.getOrders(order_id);
            }
        });

        mvvm.getOrders(order_id);

    }

    private void updateUi() {
        if (orderModel.getCaterer().getIs_delivry().equals("delivry")) {
            updateStateHasDelivery();
        } else {
            updateStateHasNoDelivery();

        }

    }

    private void updateStateHasDelivery() {
        if (orderModel.getStatus_order().equals("approval")) {
            updateState1Step1();
        } else if (orderModel.getStatus_order().equals("making")) {
            updateState1Step2();

        } else if (orderModel.getStatus_order().equals("delivery")) {
            updateState1Step3();

        } else if (orderModel.getStatus_order().equals("completed")) {
            updateState1Step4();

        }

    }


    private void updateStateHasNoDelivery() {
        if (orderModel.getStatus_order().equals("approval")) {
            updateState2Step1();
        } else if (orderModel.getStatus_order().equals("making")) {
            updateState2Step2();

        } else if (orderModel.getStatus_order().equals("completed")) {
            updateState2Step3();


        }
    }


    private void updateState1Step1() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.gray4);
        binding.step1.line2.setBackgroundResource(R.color.gray4);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(getDate(orderModel.getUpdated_at()));
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(null);


    }

    private void updateState1Step2() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_gray4);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.gray4);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(getDate(orderModel.getUpdated_at()));
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(null);

    }

    private void updateState1Step3() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_gray4);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line3.setBackgroundResource(R.color.gray4);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(getDate(orderModel.getUpdated_at()));
        binding.step1.tvDateDelivered.setText(null);
    }

    private void updateState1Step4() {
        binding.step1.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step1.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivering.setImageResource(R.drawable.circle_primary);
        binding.step1.imageDelivered.setImageResource(R.drawable.circle_primary);

        binding.step1.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line2.setBackgroundResource(R.color.colorPrimary);
        binding.step1.line3.setBackgroundResource(R.color.colorPrimary);

        binding.step1.tvDateAccepted.setText(null);
        binding.step1.tvDatePending.setText(null);
        binding.step1.tvDateDelivering.setText(null);
        binding.step1.tvDateDelivered.setText(getDate(orderModel.getUpdated_at()));

    }


    private void updateState2Step1() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_gray4);
        binding.step2.imageDone.setImageResource(R.drawable.circle_gray4);

        binding.step2.line1.setBackgroundResource(R.color.gray4);
        binding.step2.line2.setBackgroundResource(R.color.gray4);

        binding.step2.tvDateAccepted.setText(getDate(orderModel.getUpdated_at()));
        binding.step2.tvDatePending.setText(null);
        binding.step2.tvDateDone.setText(null);


    }

    private void updateState2Step2() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step2.imageDone.setImageResource(R.drawable.circle_gray4);

        binding.step2.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step2.line2.setBackgroundResource(R.color.gray4);

        binding.step2.tvDateAccepted.setText(null);
        binding.step2.tvDatePending.setText(getDate(orderModel.getUpdated_at()));
        binding.step2.tvDateDone.setText(null);


    }

    private void updateState2Step3() {
        binding.step2.imageDateAccepted.setImageResource(R.drawable.circle_primary);
        binding.step2.imagePending.setImageResource(R.drawable.circle_primary);
        binding.step2.imageDone.setImageResource(R.drawable.circle_primary);

        binding.step2.line1.setBackgroundResource(R.color.colorPrimary);
        binding.step2.line2.setBackgroundResource(R.color.colorPrimary);

        binding.step2.tvDateAccepted.setText(null);
        binding.step2.tvDatePending.setText(null);
        binding.step2.tvDateDone.setText(getDate(orderModel.getUpdated_at()));

    }

    private String getDate(String updateAt) {
        String date = "";
        if (updateAt != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date d = simpleDateFormat.parse(updateAt);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nhh:mm aa", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getDefault());
                date = dateFormat.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return date;
    }

}



