package com.apps.etbo5ly_client.uis.common_uis.activity_intro_slider;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.databinding.ActivityIntroSliderBinding;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroSliderActivity extends BaseActivity {
    private ActivityIntroSliderBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_slider);
        initView();

    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro1, getString(R.string.select_order), getString(R.string.sel_order_u_want), false));
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro2, getString(R.string.ch_food), getString(R.string.ch_appro), false));
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro3, getString(R.string.sel_address_receive), getString(R.string.ch_app_cuisine), true));

        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, new ArrayList<>());
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.pager.setAdapter(adapter);
        binding.tvSkip.setOnClickListener(v -> navigateToLoginActivity());
    }

    private void navigateToLoginActivity() {
        UserSettingsModel model = getUserSettings();
        model.setFirstTime(false);
        setUserSettings(model);
        setResult(RESULT_OK);
        finish();
    }

}