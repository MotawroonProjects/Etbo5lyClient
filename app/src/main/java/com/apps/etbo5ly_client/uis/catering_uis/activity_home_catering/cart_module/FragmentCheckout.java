package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.cart_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.databinding.FragmentCheckoutBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentCheckoutMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_zone_cover.ZoneCoverActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;
import com.apps.etbo5ly_client.uis.common_uis.activity_login.LoginActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentCheckout extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private HomeActivity activity;
    private FragmentCheckoutBinding binding;
    private FragmentCheckoutMvvm mvvm;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private ManageCartModel manageCartModel;
    private SendOrderModel model;
    private KitchenModel kitchenModel;
    private int req;
    private ActivityResultLauncher<Intent> launcher;
    private DatePickerDialog datePickerDialog;
    private double finalTotal = 0.0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                ZoneCover zoneCover = (ZoneCover) result.getData().getSerializableExtra("data");
                model.setZone(zoneCover.getZone().getTitel());
                model.setDelivery_cost(zoneCover.getZone_cost());
                model.setZone_id(zoneCover.getZone_id());
                binding.setModel(model);
                calculateTotal();
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK) {
                //user logged in
                UserSettingsModel userSettingsModel = getUserSettings();
                userSettingsModel.setCanFinishLogin(true);
                setUserSettings(userSettingsModel);
                activityHomeGeneralMvvm.onUserDateRefresh().setValue(true);
                activity.updateFireBase();
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
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.flLoader.setVisibility(View.VISIBLE);
                binding.llData.setVisibility(View.VISIBLE);

            } else {
                binding.flLoader.setVisibility(View.GONE);

            }
        });

        mvvm.getOnCouponDataSuccess().observe(activity, couponModel -> {
            model.setCoupon_value(couponModel.getAmount());
            model.setCopon(couponModel.getName());
            binding.setModel(model);
            binding.setCouponValue(Integer.parseInt(couponModel.getAmount()));
            calculateTotal();
        });

        mvvm.getOnDataSuccess().observe(activity, m -> {
            kitchenModel = m;
            binding.setKitchen(kitchenModel);
            if (kitchenModel != null) {
                binding.llData.setVisibility(View.VISIBLE);
                model.setHasZone(kitchenModel.getZone_cover().size() > 0);
            }

        });

        mvvm.getOnOrderSuccess().observe(activity, orderModel -> {
            manageCartModel.clearCart(activity);
            NavController navController = Navigation.findNavController(activity, R.id.fragmentBaseCart);
            navController.navigateUp();
            activityHomeGeneralMvvm.onCartRefresh().setValue(true);
            activityHomeGeneralMvvm.onOrderRefresh().setValue(orderModel);
            activityHomeGeneralMvvm.onOrdersRefresh().setValue(true);
            Bundle bundle = new Bundle();
            bundle.putString("order_id", orderModel.getId());
            navController.navigate(R.id.fragmentOrderSuccess, bundle);

        });

        createDateDialog();
        manageCartModel = ManageCartModel.newInstance();
        model = manageCartModel.getSendOrderModel(activity);
        model.setContext(activity);
        binding.setLang(getLang());
        binding.setTotal(0.0);
        binding.setCouponValue(0);
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

        binding.rbCash.setOnClickListener(v -> {
            model.setPaid_type("cash");
        });

        binding.rbOnline.setOnClickListener(v -> {
            model.setPaid_type("online");
        });

        binding.llZone.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(activity, ZoneCoverActivity.class);
            intent.putExtra("caterer_id", model.getCaterer_id());
            launcher.launch(intent);
        });

        binding.llDate.setOnClickListener(v -> {
            try {
                datePickerDialog.show(getChildFragmentManager(), "");

            } catch (Exception exception) {

            }
        });

        binding.btnCheck.setOnClickListener(v -> {
            if (getUserModel() != null) {
                String coupon_code = binding.edtCoupon.getText().toString();
                mvvm.checkCoupon(getUserModel(), coupon_code, activity);
                Common.CloseKeyBoard(activity, binding.edtCoupon);
            } else {
                navigateToLoginActivity();
            }

        });

        binding.btnSend.setOnClickListener(v -> {
            if (getUserModel() != null) {
                model.setUser_id(getUserModel().getData().getId());
                model.setOption_id(getUserSettings().getOption_id());
                model.setTotal(finalTotal + "");
                mvvm.sendOrder(model, activity);

            } else {
                navigateToLoginActivity();
            }
        });


        mvvm.getZone(model.getCaterer_id());


    }

    private void navigateToLoginActivity() {
        req = 2;
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("from", "cart");
        launcher.launch(intent);
    }

    private void createDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ContextCompat.getColor(activity, R.color.gray4));
        datePickerDialog.setOkColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        datePickerDialog.setCancelText(R.string.dismiss);
        datePickerDialog.setOkText(R.string.select);
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setLocale(new Locale(getLang()));
        datePickerDialog.setTitle(getString(R.string.booking_date));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);


    }

    private void calculateTotal() {
        double total = manageCartModel.getTotal(activity);
        double tax = Double.parseDouble(kitchenModel.getTax());
        double delivery = Double.parseDouble(model.getDelivery_cost());
        double service_cost = Double.parseDouble(kitchenModel.getCustomers_service());
        double discount = Double.parseDouble(kitchenModel.getDiscount());
        double coupon = Double.parseDouble(model.getCoupon_value());

        double taxValue = (tax / 100) * total;
        double serviceValue = (service_cost / 100) * total;
        double discountValue = (discount / 100) * total;
        double couponValue = (coupon / 100) * total;
        double totalAfterDiscount = total - (discountValue + couponValue);

        finalTotal = totalAfterDiscount + delivery + taxValue + serviceValue;

        binding.setTotal(finalTotal);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String date = dateFormat.format(calendar.getTime());
        model.setBooking_date(date);
        binding.setModel(model);


    }
}
