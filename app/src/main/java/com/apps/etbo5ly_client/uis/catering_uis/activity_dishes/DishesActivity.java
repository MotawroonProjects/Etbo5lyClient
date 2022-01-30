package com.apps.etbo5ly_client.uis.catering_uis.activity_dishes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetDishesAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.CategoryDishesAdapter;
import com.apps.etbo5ly_client.databinding.ActivityDishesBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityDishesMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends BaseActivity {
    private ActivityDishesBinding binding;
    private CategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;
    private ActivityDishesMvvm mvvm;
    private double total = 0.0;
    private int dishes = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dishes);
        initView();
    }


    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityDishesMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.dishes), R.color.colorPrimary, R.color.white);

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });

        mvvm.onDataSuccess().observe(this, categories -> {
            if (categories.size() > 0) {
                updateUi();
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }

        });


        adapter = new CategoryDishesAdapter(this);
        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        dishesAdapter = new BuffetDishesAdapter(this);
        binding.recView.setAdapter(dishesAdapter);

        binding.setDishes(dishes + "");
        binding.setTotal(total + "");


        mvvm.getDishes();

    }

    private void updateUi() {
        BuffetModel.Category category = mvvm.onDataSuccess().getValue().get(0);
        category.setSelected(true);

        mvvm.onDataSuccess().getValue().set(0, category);
        adapter.updateList(mvvm.onDataSuccess().getValue());
        updateDishes(category);
    }

    private void updateDishes(BuffetModel.Category category) {


        if (category.getDishes_buffet().size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
        } else {

            binding.tvNoData.setVisibility(View.VISIBLE);
        }
        dishesAdapter.updateList(category.getDishes_buffet());


    }

    public void setItemCategory(BuffetModel.Category category, int currentPos) {
        mvvm.setSelectedCategoryPos(currentPos);

        updateDishes(category);
    }


    public void updateCart(DishModel model, int absoluteAdapterPosition) {

    }
}