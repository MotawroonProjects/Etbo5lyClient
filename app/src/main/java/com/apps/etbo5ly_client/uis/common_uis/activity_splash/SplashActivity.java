package com.apps.etbo5ly_client.uis.common_uis.activity_splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.language.Language;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_app_category.AppCategoryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.apps.etbo5ly_client.databinding.ActivitySplashBinding;
import com.apps.etbo5ly_client.uis.common_uis.activity_intro_slider.IntroSliderActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_language_country.LanguageCountryActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_login.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();

    }

    private void initView() {

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        setUpData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    String lang = result.getData().getStringExtra("lang");
                    refreshActivity(lang);
                } else {
                    setUpData();
                }
            } else if (req == 2 && result.getResultCode() == RESULT_OK) {
                setUpData();
            } else if (req == 3 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                String option_id = result.getData().getStringExtra("option_id");
                navigateToHomeActivity(option_id);
            }
        });

    }

    private void navigateToHomeActivity(String option_id) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("option_id", option_id);
        startActivity(intent);
        finish();
    }


    private void setUpData() {
        req = 1;
        if (getUserSettings() == null) {
            navigateToLanguageCountryActivity();
        } else {
            if (getUserSettings().getCityModel() == null) {
                navigateToLanguageCountryActivity();

            } else if (getUserSettings().isFirstTime()) {
                navigateToIntroSliderActivity();

            } else {

                if (getUserModel() != null) {
                    if (!getUserSettings().getOption_id().isEmpty()) {
                        navigateToHomeActivity(getUserSettings().getOption_id());
                    } else {
                        navigateToAppCategoryActivity("login");

                    }
                } else {
                    navigateToLoginActivity();
                }

            }
        }

    }

    private void navigateToAppCategoryActivity(String action) {
        req = 3;
        Intent intent = new Intent(this, AppCategoryActivity.class);
        intent.putExtra("action", action);
        launcher.launch(intent);
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToIntroSliderActivity() {
        req = 2;
        Intent intent = new Intent(this, IntroSliderActivity.class);
        launcher.launch(intent);
    }

    private void navigateToLanguageCountryActivity() {
        Intent intent = new Intent(this, LanguageCountryActivity.class);
        launcher.launch(intent);
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

