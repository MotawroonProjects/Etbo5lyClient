package com.apps.etbo5ly_client.uis.common_uis.activity_language_country;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.model.SelectedLocation;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.databinding.ActivityLanguageCountryBinding;
import com.apps.etbo5ly_client.mvvm.mvvm_common.ActivityCountryLanguageMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_country.CountryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_map.MapActivity;

public class LanguageCountryActivity extends BaseActivity {
    private ActivityLanguageCountryBinding binding;
    private String lang = "ar";
    private ActivityCountryLanguageMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;
    private int req;
    private String selectedLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_country);
        initView();
    }

    private void initView() {
        lang = getLang();
        selectedLang = lang;
        mvvm = ViewModelProviders.of(this).get(ActivityCountryLanguageMvvm.class);


        binding.llAr.setOnClickListener(v -> {
            if (!selectedLang.equals("ar")) {
                selectedLang = "ar";
                binding.tvAr.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                binding.imageArChecked.setVisibility(View.VISIBLE);

                binding.tvEn.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.imageEnChecked.setVisibility(View.INVISIBLE);
            }


        });


        binding.llEn.setOnClickListener(v -> {
            if (!selectedLang.equals("en")) {
                selectedLang = "en";
                binding.tvEn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                binding.imageEnChecked.setVisibility(View.VISIBLE);

                binding.tvAr.setTextColor(ContextCompat.getColor(this, R.color.black));
                binding.imageArChecked.setVisibility(View.INVISIBLE);


            }


        });

        binding.llCountry.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(this, CountryActivity.class);
            launcher.launch(intent);

        });

        binding.btnSave.setOnClickListener(v -> {
            if (mvvm.getSelectedCountry().getValue() != null && mvvm.getSelectedCity().getValue() != null && mvvm.getSelectedLocation().getValue() != null) {
                UserSettingsModel userSettingsModel = getUserSettings();
                if (userSettingsModel == null) {
                    userSettingsModel = new UserSettingsModel();
                }
                userSettingsModel.setCityModel(mvvm.getSelectedCity().getValue());
                userSettingsModel.setCountryModel(mvvm.getSelectedCountry().getValue());
                userSettingsModel.setLocation(mvvm.getSelectedLocation().getValue());

                setUserSettings(userSettingsModel);
                if (!selectedLang.equals(lang)) {
                    Intent intent = getIntent();
                    intent.putExtra("lang", selectedLang);
                    setResult(RESULT_OK, intent);

                } else {
                    setResult(RESULT_OK);

                }
                finish();
            } else {
                Common.showSnackBar(binding.root, getString(R.string.plz_ch_country));
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                CountryModel countryModel = (CountryModel) result.getData().getSerializableExtra("country");
                CountryModel cityModel = (CountryModel) result.getData().getSerializableExtra("city");


                mvvm.setSelectedCountry(countryModel);
                mvvm.setSelectedCity(cityModel);
                binding.setCity(mvvm.getSelectedCountry().getValue().getTitel() + " - " + mvvm.getSelectedCity().getValue().getTitel());

                navigateToMapActivity();
            } else if (req == 2 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                mvvm.setSelectedLocation(location);
            }
        });
    }

    private void navigateToMapActivity() {
        req = 2;
        Intent intent = new Intent(this, MapActivity.class);
        launcher.launch(intent);
    }

}