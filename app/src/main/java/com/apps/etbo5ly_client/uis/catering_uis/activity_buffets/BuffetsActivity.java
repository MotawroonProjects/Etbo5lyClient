package com.apps.etbo5ly_client.uis.catering_uis.activity_buffets;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetsAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityBuffetsMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_details.BuffetDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class BuffetsActivity extends BaseActivity {
    private ActivityBuffetsBinding binding;
    private BuffetsAdapter adapter;
    private ActivityBuffetsMvvm mvvm;
    private String kitchen_id = "";
    private int req;
    private ActivityResultLauncher<Intent> launcher;

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
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);


            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }

            if (adapter != null) {
                adapter.updateList(buffetsList);
            }
        });
        adapter = new BuffetsAdapter(this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        mvvm.getBuffets(kitchen_id, this);

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getBuffets(kitchen_id, this));

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                BuffetModel buffetModel = (BuffetModel) result.getData().getSerializableExtra("data");
                if (mvvm.getSelectedPos().getValue() != -1) {
                    mvvm.onDataSuccess().getValue().set(mvvm.getSelectedPos().getValue(), buffetModel);
                    adapter.notifyItemChanged(mvvm.getSelectedPos().getValue());
                    mvvm.getSelectedPos().setValue(-1);
                }

            }
        });
    }


    public void setItemData(BuffetModel buffetModel, int adapterPosition) {
        req = 1;
        mvvm.getSelectedPos().setValue(adapterPosition);

        Intent intent = new Intent(this, BuffetDetailsActivity.class);
        intent.putExtra("data", buffetModel);
        launcher.launch(intent);
    }
}