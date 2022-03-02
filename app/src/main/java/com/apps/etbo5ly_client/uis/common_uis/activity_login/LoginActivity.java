package com.apps.etbo5ly_client.uis.common_uis.activity_login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.databinding.ActivityLoginBinding;
import com.apps.etbo5ly_client.databinding.BottomSheetDialogBinding;
import com.apps.etbo5ly_client.model.LoginModel;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_app_category.AppCategoryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_verification_code.VerificationCodeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private boolean isAccepted = false;
    private int req;
    private ActivityResultLauncher<Intent> launcher;
    private String isFromSplash = "";

    @Override
    protected void onResume() {
        super.onResume();
        if (getUserSettings().isCanFinishLogin() && getUserModel() != null) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("from")) {
            isFromSplash = intent.getStringExtra("from");
        } else {
            isFromSplash = "splash";
        }
    }

    private void initView() {
        model = new LoginModel();
        binding.setModel(model);
        if (!isFromSplash.equals("splash")) {
            binding.tvSkip.setVisibility(View.GONE);
        }
        binding.tvSkip.setPaintFlags(binding.tvSkip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
                openSheet();
            }
        });

        binding.tvSkip.setOnClickListener(v -> {
            if (getUserSettings() != null && !getUserSettings().getOption_id().isEmpty()) {
                navigateToHomeActivity(getUserSettings().getOption_id());
            } else {
                navigateToAppCategoryActivity("skip");

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
                if (isFromSplash.equals("splash")) {
                    if (getUserSettings() != null && !getUserSettings().getOption_id().isEmpty()) {
                        navigateToHomeActivity(getUserSettings().getOption_id());

                    } else {
                        navigateToAppCategoryActivity("login");

                    }

                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            } else if (req == 2 && result.getResultCode() == RESULT_OK) {

                UserSettingsModel userSettingsModel = getUserSettings();
                if (userSettingsModel != null && !userSettingsModel.getOption_id().isEmpty()) {
                    navigateToHomeActivity(getUserSettings().getOption_id());
                } else {
                    navigateToAppCategoryActivity("login");

                }

            }
        });
    }

    private void navigateToHomeActivity(String option_id) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("option_id", option_id);
        startActivity(intent);
        if (getUserModel() != null) {
            finish();
        }

    }

    private void navigateToVerificationCodeActivity() {
        req = 1;
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        launcher.launch(intent);
    }


    private void navigateToAppCategoryActivity(String action) {
        req = 2;
        Intent intent = new Intent(this, AppCategoryActivity.class);
        intent.putExtra("action", action);
        launcher.launch(intent);


    }

    private void openSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        BottomSheetDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottom_sheet_dialog, null, false);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCanceledOnTouchOutside(false);
        dialogBinding.tv.setText(Html.fromHtml(getString(R.string.do_read_our_terms_of_use_and_privacy_policy)));
        dialogBinding.tv.setMovementMethod(LinkMovementMethod.getInstance());
        dialogBinding.tv.setClickable(true);
        dialogBinding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> isAccepted = isChecked);
        dialogBinding.btnConfirm.setOnClickListener(v -> {
            if (isAccepted) {
                navigateToVerificationCodeActivity();
            } else {
                Common.showSnackBar(binding.root, getString(R.string.cnt_login));
            }

            dialog.dismiss();

        });
        dialog.show();


    }


}