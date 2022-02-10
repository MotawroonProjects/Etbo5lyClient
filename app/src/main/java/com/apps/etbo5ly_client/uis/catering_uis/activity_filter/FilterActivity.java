package com.apps.etbo5ly_client.uis.catering_uis.activity_filter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.FilterAdapter;
import com.apps.etbo5ly_client.databinding.ActivityFilterBinding;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityFilterMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_filter_setting.FilterSettingActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class FilterActivity extends BaseActivity {
    private ActivityFilterBinding binding;
    private ActivityFilterMvvm mvvm;
    private FilterModel filterModel;
    private FilterAdapter adapter;
    private int req;
    private ActivityResultLauncher<Intent> launcher;
    private boolean isDataChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        filterModel = (FilterModel) intent.getSerializableExtra("filter");
        if (filterModel == null) {
            filterModel = new FilterModel();
            if (getUserModel()!=null){
                filterModel.setUser_id(getUserModel().getData().getId());

            }
            filterModel.setLatitude(getUserSettings().getLocation().getLat()+"");
            filterModel.setLongitude(getUserSettings().getLocation().getLng()+"");

        }
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.filter), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityFilterMvvm.class);
        mvvm.getIsDataLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.loader.startShimmer();
            } else {
                binding.loader.stopShimmer();
                binding.loader.setVisibility(View.GONE);

            }
        });

        mvvm.onKitchenDataSuccess().observe(this, kitchenList -> {
            if (kitchenList.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);


            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
            if (adapter != null) {
                adapter.updateList(kitchenList);

            }

        });
        mvvm.onAddRemoveFavoriteSuccess().observe(this,itemPos->{
            if (adapter!=null){
                adapter.notifyItemChanged(itemPos);

            }

            isDataChanged = true;
        });
        adapter = new FilterAdapter(this);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewLayout.recView.setAdapter(adapter);

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getData(filterModel));

        binding.llFilter.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(this, FilterSettingActivity.class);
            intent.putExtra("filter", filterModel);
            launcher.launch(intent);
        });

        binding.llSort.setOnClickListener(this::createMenu);


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == RESULT_OK && result.getData() != null) {
                filterModel = (FilterModel) result.getData().getSerializableExtra("filter");
                mvvm.getData(filterModel);
            } else if (req == 2 && result.getResultCode() == RESULT_OK) {
                isDataChanged = true;
                mvvm.getData(filterModel);

            }
        });

        mvvm.getData(filterModel);


    }

    private void createMenu(View v) {

        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenu().add(0, 1, 1, R.string.most_rated);
        popupMenu.getMenu().add(0, 2, 2, R.string.nearest);
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == 1) {
                filterModel.setSort_by("most_rated");
            } else {
                filterModel.setSort_by("most_closest");

            }
            mvvm.getData(filterModel);
            popupMenu.dismiss();
            return false;
        });

        popupMenu.show();

    }

    public void setItemKitchen(KitchenModel model, int adapterPosition) {
        req = 2;
        Intent intent = new Intent(this, KitchenDetailsActivity.class);
        intent.putExtra("kitchen_id", model.getId());
        launcher.launch(intent);
    }

    public void addRemoveFavorite(KitchenModel model, int adapterPosition) {
        mvvm.addRemoveFavorite(getUserModel(),adapterPosition);
    }

    @Override
    public void onBackPressed() {
        if (isDataChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}