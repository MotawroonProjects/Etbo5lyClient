package com.apps.etbo5ly_client.uis.common_uis.activity_address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.AddressAdapter;
import com.apps.etbo5ly_client.databinding.ActivityAddressBinding;
import com.apps.etbo5ly_client.model.AddressModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_common.ActivityAddressesMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class AddressActivity extends BaseActivity {
    private ActivityAddressBinding binding;
    private AddressAdapter adapter;
    private ActivityAddressesMvvm mvvm;
    private String type;
    private int pos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }


    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.address), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityAddressesMvvm.class);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);

        });
        mvvm.onDataSuccess().observe(this, list -> {
            if (list.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }

            if (adapter != null) {
                adapter.updateList(list);
            }
        });

        mvvm.onDeleteSuccess().observe(this, addressModel -> {

            Intent intent = getIntent();
            intent.putExtra("action", "delete");
            intent.putExtra("data", addressModel);
            setResult(RESULT_OK, intent);
            finish();
        });


        binding.recViewLayout.tvNoData.setText(R.string.no_addresses);
        adapter = new AddressAdapter(this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getAddresses(type, getUserModel());
        });

        mvvm.getAddresses(type, getUserModel());
    }


    public void setItemData(AddressModel addressModel) {
        Intent intent = getIntent();
        intent.putExtra("action", "add");
        intent.putExtra("data", addressModel);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void deleteAddress(AddressModel addressModel, int adapterPosition) {
        pos = adapterPosition;
        mvvm.deleteAddress(addressModel,this);
    }
}