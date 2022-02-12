package com.apps.etbo5ly_client.uis.common_uis.activity_app_category;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.ActivityAppCategoryBinding;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class AppCategoryActivity extends BaseActivity {
    private ActivityAppCategoryBinding binding;
    private String action = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_category);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
    }

    private void initView() {
        binding.consBanquet.setOnClickListener(v -> {
            navigateToActivity("1");
        });
        binding.consChef.setOnClickListener(v -> {
            navigateToActivity("2");
        });
        binding.consTruck.setOnClickListener(v -> {
            navigateToActivity("3");
        });
    }

    private void navigateToActivity(String option_id) {
        UserSettingsModel userSettingsModel = getUserSettings();
        if (userSettingsModel==null){
            userSettingsModel = new UserSettingsModel();
        }
        userSettingsModel.setOption_id(option_id);
        setUserSettings(userSettingsModel);
        Intent intent = getIntent();
        intent.putExtra("option_id", option_id);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        if (!action.equals("login")) {
            super.onBackPressed();
        }
    }
}