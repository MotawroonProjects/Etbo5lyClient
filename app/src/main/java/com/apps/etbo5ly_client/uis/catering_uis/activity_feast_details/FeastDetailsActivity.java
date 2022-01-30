package com.apps.etbo5ly_client.uis.catering_uis.activity_feast_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetMenuAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetDetailsBinding;
import com.apps.etbo5ly_client.databinding.ActivityFeastDetailsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class FeastDetailsActivity extends BaseActivity {
    private ActivityFeastDetailsBinding binding;
    private BuffetModel model;
    private BuffetMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feast_details);
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