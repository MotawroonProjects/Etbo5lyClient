package com.apps.etbo5ly_client.uis.common_uis.activity_city;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;

import com.apps.etbo5ly_client.adapters.common_adapter.CityAdapter;
import com.apps.etbo5ly_client.databinding.ActivityCityBinding;
import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.mvvm.mvvm_common.ActivityCityMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class CityActivity extends BaseActivity {
    private ActivityCityBinding binding;
    private String lang = "";
    private ActivityCityMvvm mvvm;
    private CityAdapter adapter;
    private String country_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        country_id = intent.getStringExtra("data");
    }

    private void initView() {
        setUpToolbar(binding.toolBar, getString(R.string.ch_city), R.color.white, R.color.black);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        lang = getLang();
        mvvm = ViewModelProviders.of(this).get(ActivityCityMvvm.class);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            }
        });

        mvvm.getCityLiveData().observe(this, citiesList -> {
            if (adapter != null) {
                adapter.updateList(citiesList);
            }
        });

        adapter = new CityAdapter(this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getCity(country_id));


        mvvm.getCity(country_id);
    }


    public void setItemCity(CountryModel countryModel) {
        Intent intent = getIntent();
        intent.putExtra("city", countryModel);
        setResult(RESULT_OK, intent);
        finish();

    }
}