package com.apps.etbo5ly_client.uis.catering_uis.activity_zone_cover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.ZoneAdapter;
import com.apps.etbo5ly_client.adapters.common_adapter.CityAdapter;
import com.apps.etbo5ly_client.databinding.ActivityCityBinding;
import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.model.ZoneCover;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityZoneMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_common.ActivityCityMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class ZoneCoverActivity extends BaseActivity {
    private ActivityCityBinding binding;
    private ActivityZoneMvvm mvvm;
    private ZoneAdapter adapter;
    private String caterer_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        caterer_id = intent.getStringExtra("caterer_id");
    }

    private void initView() {
        setUpToolbar(binding.toolBar, getString(R.string.covered_area), R.color.white, R.color.black);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mvvm = ViewModelProviders.of(this).get(ActivityZoneMvvm.class);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            }
        });

        mvvm.getOnDataSuccess().observe(this, zoneList -> {

            if (adapter != null) {
                adapter.updateList(zoneList);
            }
        });

        adapter = new ZoneAdapter(this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getZone(caterer_id));
        mvvm.getZone(caterer_id);

    }


    public void setItemZone(ZoneCover zoneCover) {
        Intent intent = getIntent();
        intent.putExtra("data", zoneCover);
        setResult(RESULT_OK, intent);
        finish();
    }
}