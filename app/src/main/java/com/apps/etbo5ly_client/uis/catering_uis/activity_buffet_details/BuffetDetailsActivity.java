package com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_details;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetDishesAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetMenuAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.CategoryDishesAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetDetailsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BuffetDetailsActivity extends BaseActivity {
    private ActivityBuffetDetailsBinding binding;
    private BuffetModel model;
    private BuffetMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BuffetModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        binding.setLang(getLang());
        binding.setModel(model);
        adapter = new BuffetMenuAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateList(model.getCategor_dishes());
        binding.recView.setAdapter(adapter);
        binding.recView.setNestedScrollingEnabled(false);

        binding.llBack.setOnClickListener(v -> {
            finish();
        });
    }


}