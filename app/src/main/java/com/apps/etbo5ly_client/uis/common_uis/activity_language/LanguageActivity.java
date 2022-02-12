package com.apps.etbo5ly_client.uis.common_uis.activity_language;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.ActivityLanguageBinding;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;


public class LanguageActivity extends BaseActivity {
    private ActivityLanguageBinding binding;
    private String lang = "";
    private String selectedLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        initView();
    }

    private void initView() {
        lang = getLang();
        selectedLang = lang;
        binding.setLang(selectedLang);



        binding.llAr.setOnClickListener(view -> {
            selectedLang = "ar";

            if (!selectedLang.equals(lang)) {
                binding.btnSave.setVisibility(View.VISIBLE);

            } else {
                binding.btnSave.setVisibility(View.INVISIBLE);

            }
            binding.setLang(selectedLang);

        });

        binding.llEn.setOnClickListener(view -> {
            selectedLang = "en";
            if (!selectedLang.equals(lang)) {
                binding.btnSave.setVisibility(View.VISIBLE);

            } else {
                binding.btnSave.setVisibility(View.INVISIBLE);

            }
            binding.setLang(selectedLang);

        });

        binding.btnSave.setOnClickListener(view -> {

            Intent intent = getIntent();
            intent.putExtra("lang", selectedLang);
            setResult(RESULT_OK, intent);
            finish();
        });
    }


}