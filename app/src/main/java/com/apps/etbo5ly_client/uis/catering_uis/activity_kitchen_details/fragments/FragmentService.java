package com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.ChooseDialogBinding;
import com.apps.etbo5ly_client.databinding.FragmentServiceBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffets.BuffetsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_dishes.DishesActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_feasts.FeastsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class FragmentService extends BaseFragment {
    private FragmentServiceBinding binding;
    private KitchenDetailsActivity activity;
    private KitchenModel model;

    public static FragmentService newInstance(KitchenModel model) {
        FragmentService fragment = new FragmentService();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = (KitchenModel) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.cardViewBuffet.setOnClickListener(v -> createChooseDialog("buffet"));
        binding.cardViewBanquet.setOnClickListener(v -> createChooseDialog("banquet"));

    }

    private void createChooseDialog(String type) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        ChooseDialogBinding chooseDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.choose_dialog, null, false);
        chooseDialogBinding.setLang(getLang());
        chooseDialogBinding.setType(type);

        chooseDialogBinding.llBuffet.setOnClickListener(v -> {
            if (type.equals("buffet")) {
                navigateToBuffetActivity();
            } else {
                navigateToFeastsActivity();
            }
            dialog.cancel();

        });

        chooseDialogBinding.llDishes.setOnClickListener(v -> {

            navigateToDishesActivity();

            dialog.cancel();
        });

        dialog.setView(chooseDialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_style_bg);
        dialog.show();
        activity.updateBlur(20f);

        dialog.setOnCancelListener(dialog1 -> activity.updateBlur(0));

    }

    private void navigateToBuffetActivity() {
        Intent intent = new Intent(activity, BuffetsActivity.class);
        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }

    private void navigateToFeastsActivity() {
        Intent intent = new Intent(activity, FeastsActivity.class);
        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }

    private void navigateToDishesActivity() {
        Intent intent = new Intent(activity, DishesActivity.class);
        intent.putExtra("kitchen_id", model.getId());
        startActivity(intent);
    }
}