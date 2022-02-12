package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentCatererSettingBinding;
import com.apps.etbo5ly_client.databinding.FragmentProfileBinding;
import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.model.SelectedLocation;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentLanguageMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;
import com.apps.etbo5ly_client.uis.common_uis.activity_country.CountryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_language.LanguageActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_map.MapActivity;


public class FragmentCatererSetting extends BaseFragment {
    private FragmentCatererSettingBinding binding;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentLanguageMvvm mvvm;
    private HomeActivity activity;
    private ActivityResultLauncher<Intent> launcher;
    private int req;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                String lang = result.getData().getStringExtra("lang");
                activity.refreshActivity(lang);
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                CountryModel countryModel = (CountryModel) result.getData().getSerializableExtra("country");
                CountryModel cityModel = (CountryModel) result.getData().getSerializableExtra("city");


                mvvm.getSelectedCountry().setValue(countryModel);
                mvvm.getSelectedCity().setValue(cityModel);

                navigateToMapActivity();
            }else if (req == 3 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                mvvm.getSelectedLocation().setValue(location);

                UserSettingsModel userSettingsModel = getUserSettings();
                userSettingsModel.setCountryModel(mvvm.getSelectedCountry().getValue());
                userSettingsModel.setCityModel(mvvm.getSelectedCity().getValue());
                userSettingsModel.setLocation(mvvm.getSelectedLocation().getValue());
                binding.setSettings(userSettingsModel);
                setUserSettings(userSettingsModel);
                activityHomeGeneralMvvm.updateLocation(userSettingsModel);

            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_caterer_setting, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        mvvm = ViewModelProviders.of(activity).get(FragmentLanguageMvvm.class);

        binding.setSettings(getUserSettings());
        binding.setLang(getLang());

        binding.switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserSettingsModel userSettingsModel = getUserSettings();
            userSettingsModel.setCanSendNotifications(isChecked);
            setUserSettings(userSettingsModel);
        });

        binding.llChangeLanguage.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(activity, LanguageActivity.class);
            launcher.launch(intent);
        });
        binding.llCountry.setOnClickListener(v -> {
            req = 2;
            Intent intent = new Intent(activity, CountryActivity.class);
            launcher.launch(intent);

        });

    }

    private void navigateToMapActivity() {

        req = 3;
        Intent intent = new Intent(activity, MapActivity.class);
        launcher.launch(intent);
    }

}