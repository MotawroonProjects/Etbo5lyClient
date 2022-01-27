package com.apps.etbo5ly_client.uis.catering_uis.activity_buffets;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetsAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityBuffetsMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_dishes.BuffetDishesActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class BuffetsActivity extends BaseActivity {
    private ActivityBuffetsBinding binding;
    private BuffetsAdapter adapter;
    private ActivityBuffetsMvvm mvvm;
    private String kitchen_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffets);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        kitchen_id = intent.getStringExtra("kitchen_id");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.buffets), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityBuffetsMvvm.class);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.onDataSuccess().observe(this, buffetsList -> {
            if (buffetsList.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(buffetsList);
                    binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);
                }
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

            }
        });
        adapter = new BuffetsAdapter(this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        mvvm.getBuffets(kitchen_id);

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getBuffets(kitchen_id));
    }


    public void setItemData(BuffetModel buffetModel) {
        Intent intent = new Intent(this, BuffetDishesActivity.class);
        intent.putExtra("data", buffetModel);
        startActivity(intent);
    }
}