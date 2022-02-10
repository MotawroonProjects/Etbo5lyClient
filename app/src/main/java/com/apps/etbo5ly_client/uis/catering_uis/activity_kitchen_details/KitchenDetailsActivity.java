package com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.databinding.ActivityKitchenDetailsBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityKitchenDetailsMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments.FragmentCatererOffer;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments.FragmentComments;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments.FragmentGallery;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments.FragmentService;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class KitchenDetailsActivity extends BaseActivity {
    private ActivityKitchenDetailsBinding binding;
    private ActivityKitchenDetailsMvvm mvvm;
    private String kitchen_id;
    private KitchenModel model;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private String oldFav = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kitchen_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        kitchen_id = intent.getStringExtra("kitchen_id");

    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        mvvm = ViewModelProviders.of(this).get(ActivityKitchenDetailsMvvm.class);
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        binding.setLang(getLang());

        mvvm.onFavoriteSuccess().observe(this, model -> {
            this.model = model;
            binding.setModel(model);


        });
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.coordinator.setVisibility(View.GONE);
                binding.loader.setVisibility(View.VISIBLE);
                binding.loader.startShimmer();
            } else {
                binding.coordinator.setVisibility(View.VISIBLE);
                binding.loader.setVisibility(View.GONE);
                binding.loader.stopShimmer();
            }
        });
        mvvm.onDataSuccess().observe(this, kitchenModel -> {
            model = kitchenModel;
            updateUi();
        });
        String user_id = null;
        if (getUserModel() != null) {
            user_id = getUserModel().getData().getId();
        }


        mvvm.getKitchenData(kitchen_id, user_id);

        binding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int total = appBarLayout.getTotalScrollRange() + verticalOffset;
            if (total > 0) {
                binding.tvTitle.setVisibility(View.GONE);
                binding.tvTitleBottom.setVisibility(View.VISIBLE);
            } else {
                binding.tvTitle.setVisibility(View.VISIBLE);
                binding.tvTitleBottom.setVisibility(View.GONE);
            }
        });

        binding.imageFav.setOnClickListener(v -> {
            mvvm.addRemoveFavorite(getUserModel(), model);
        });

        binding.llBack.setOnClickListener(v -> {
            if (model != null && model.getIs_favorite() != null && !model.getIs_favorite().equals(oldFav)) {
                setResult(RESULT_OK);
            }
            finish();
        });
    }

    private void updateUi() {
        oldFav = model.getIs_favorite();

        binding.setModel(model);

        fragmentList.add(FragmentService.newInstance(model));
        fragmentList.add(FragmentCatererOffer.newInstance(model));
        fragmentList.add(FragmentGallery.newInstance(model));
        fragmentList.add(FragmentComments.newInstance(model));

        titles.add(getString(R.string.services));
        titles.add(getString(R.string.offers));
        titles.add(getString(R.string.gallery2));
        titles.add(getString(R.string.rates));


        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, titles);
        binding.pager.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
    }

    public void updateBlur(float blur) {
        if (blur > 0) {
            binding.blur.setVisibility(View.VISIBLE);
        } else {
            binding.blur.setVisibility(View.GONE);

        }
        binding.blur.setBlurRadius(blur);

    }

    @Override
    public void onBackPressed() {
        if (model != null && model.getIs_favorite() != null && !model.getIs_favorite().equals(oldFav)) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}