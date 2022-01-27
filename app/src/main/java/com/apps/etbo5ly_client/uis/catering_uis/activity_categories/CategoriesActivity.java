package com.apps.etbo5ly_client.uis.catering_uis.activity_categories;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.Category2Adapter;
import com.apps.etbo5ly_client.databinding.ActivityCategoriesBinding;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityCategoriesMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_filter.FilterActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.List;

public class CategoriesActivity extends BaseActivity {
    private ActivityCategoriesBinding binding;
    private Category2Adapter adapter;
    private ActivityCategoriesMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_categories);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.categories), R.color.colorPrimary, R.color.white);
        mvvm = ViewModelProviders.of(this).get(ActivityCategoriesMvvm.class);
        mvvm.getIsCategoryDataLoading().observe(this, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.loader.startShimmer();
            } else {
                binding.loader.stopShimmer();
                binding.loader.setVisibility(View.GONE);

            }
        });

        mvvm.onCategoryDataSuccess().observe(this, categoryList -> {
            if (categoryList.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(categoryList);
                    binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);
                }
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

            }
        });
        adapter = new Category2Adapter(this);
        binding.recViewLayout.recView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recViewLayout.recView.setAdapter(adapter);

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        mvvm.getCategoryData();

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getCategoryData());
    }

    public void setItemCategory(CategoryModel categoryModel) {
        navigateToFilterActivity("all", categoryModel.getId());
    }

    private void navigateToFilterActivity(String type, String category_id) {
        FilterModel filterModel = new FilterModel();
        if (!category_id.isEmpty()) {
            List<String> ids = filterModel.getCategory_id();
            ids.add(category_id);
            filterModel.setCategory_id(ids);
        }

        filterModel.setIs_type(type);

        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("filter", filterModel);
        startActivity(intent);
    }
}