package com.apps.etbo5ly_client.uis.common_uis.activity_base;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.apps.etbo5ly_client.databinding.ToolbarBinding;
import com.apps.etbo5ly_client.common.language.Language;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.common.preferences.Preferences;

import io.paperdb.Paper;


public class BaseActivity extends AppCompatActivity {

    public static final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_PERM = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAM_PERM = Manifest.permission.CAMERA;
    public static final String fineLocPerm = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String BUFFET = "BUFFET";
    public static final String FEAST = "FEAST";
    public static final String DISH = "DISH";
    public static final String OFFER = "OFFER";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {


    }

    protected String getLang() {
        Paper.init(this);
        String lang = Paper.book().read("lang", "ar");
        return lang;
    }

    protected UserModel getUserModel() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserData(this);
    }

    protected void setUserModel(UserModel userModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.createUpdateUserData(this, userModel);
    }

    protected void clearUserData() {
        Preferences preferences = Preferences.getInstance();
        preferences.clearUserData(this);
    }


    public void setUserSettings(UserSettingsModel userSettingsModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.create_update_user_settings(this, userSettingsModel);
    }

    public UserSettingsModel getUserSettings() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserSettings(this);
    }

    public void setRoomId(String order_id) {
        Preferences preferences = Preferences.getInstance();
        preferences.create_update_room(this, order_id);
    }

    public void clearRoomId() {
        Preferences preferences = Preferences.getInstance();
        preferences.clearRoomId(this);
    }


    protected void setUpToolbar(ToolbarBinding binding, String title, int background, int arrowTitleColor) {
        binding.setLang(getLang());
        binding.setTitle(title);
        binding.arrow.setColorFilter(ContextCompat.getColor(this, arrowTitleColor));
        binding.tvTitle.setTextColor(ContextCompat.getColor(this, arrowTitleColor));
        binding.toolbar.setBackgroundResource(background);
        binding.llBack.setOnClickListener(v -> finish());
    }


}