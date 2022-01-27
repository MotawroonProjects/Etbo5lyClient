package com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_dishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetDishesAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetsAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.CategoryDishesAdapter;
import com.apps.etbo5ly_client.databinding.ActivityBuffetDishesBinding;
import com.apps.etbo5ly_client.databinding.ActivityBuffetsBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityBuffetsMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityKitchenDetailsMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BuffetDishesActivity extends BaseActivity {
    private ActivityBuffetDishesBinding binding;
    private BuffetModel model;
    private List<BuffetModel.Category> categoryList;
    private List<DishModel> dishModelList;
    private CategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;
    private double total = 0.0;
    private int dishes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buffet_dishes);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (BuffetModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        categoryList = new ArrayList<>();
        dishModelList = new ArrayList<>();
        setUpToolbar(binding.toolbar, model.getTitel(), R.color.colorPrimary, R.color.white);
        adapter = new CategoryDishesAdapter(this);
        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        dishesAdapter = new BuffetDishesAdapter(this);
        binding.recView.setAdapter(dishesAdapter);
        updateUi();

        binding.setDishes(dishes + "");
        binding.setTotal(total + "");

    }

    private void updateUi() {
        categoryList.addAll(model.getCategor_dishes());
        BuffetModel.Category category = categoryList.get(0);
        category.setSelected(true);
        categoryList.set(0, category);
        adapter.updateList(categoryList);
        updateDishes(category);
    }

    private void updateDishes(BuffetModel.Category category) {
        dishModelList.clear();
        dishModelList.addAll(category.getDishes_buffet());

        if (category.getDishes_buffet().size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
            dishesAdapter.updateList(dishModelList);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }

    }

    public void setItemCategory(BuffetModel.Category category, int currentPos) {
        updateDishes(category);
    }


    public void updateCart(DishModel model) {

    }
}