package com.apps.etbo5ly_client.uis.catering_uis.activity_feast_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetMenuAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetDetailsBinding;
import com.apps.etbo5ly_client.databinding.ActivityFeastDetailsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class FeastDetailsActivity extends BaseActivity {
    private ActivityFeastDetailsBinding binding;
    private BuffetModel model;
    private BuffetMenuAdapter adapter;
    private ManageCartModel manageCartModel;
    private String kitchen_status = "";

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
        kitchen_status = intent.getStringExtra("kitchen_status");

    }

    private void initView() {
        manageCartModel = ManageCartModel.newInstance();
        binding.setLang(getLang());
        binding.setModel(model);
        binding.setKitchenStatus(kitchen_status);

        adapter = new BuffetMenuAdapter(this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateList(model.getCategor_dishes());
        binding.recView.setAdapter(adapter);
        binding.recView.setNestedScrollingEnabled(false);

        binding.llBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnConfirm.setOnClickListener(v -> {

            if (model.isInCart()) {
                Toast.makeText(this, R.string.already_cart, Toast.LENGTH_SHORT).show();
            } else {
                SendOrderModel.Details item = new SendOrderModel.Details("", "", "", model.getId(), model.getCaterer_id(), "1", model.getPhoto(), model.getTitel(), model.getPrice(),FEAST);

                if (manageCartModel.getSendOrderModel(this).getCaterer_id().isEmpty() || manageCartModel.getSendOrderModel(this).getCaterer_id().equals(model.getCaterer_id())) {
                    manageCartModel.addItemToCart(this, item, model.getCaterer_id());
                    model.setAmountInCart(1);
                    model.setInCart(true);
                    Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.putExtra("data", model);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();

                }
            }


        });
    }


}