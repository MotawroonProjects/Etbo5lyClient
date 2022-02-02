package com.apps.etbo5ly_client.uis.catering_uis.activity_dishes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.BuffetDishesAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.CategoryDishesAdapter;
import com.apps.etbo5ly_client.databinding.ActivityDishesBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityDishesMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends BaseActivity {
    private ActivityDishesBinding binding;
    private CategoryDishesAdapter adapter;
    private BuffetDishesAdapter dishesAdapter;
    private ActivityDishesMvvm mvvm;

    private ManageCartModel manageCartModel;
    private List<DishModel> cartDishes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dishes);
        initView();
    }


    private void initView() {
        manageCartModel = ManageCartModel.newInstance();
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

        mvvm.onDishCartSuccess().observe(this, dishesList -> {
            if (dishesList.size() > 0) {
                cartDishes.clear();
                cartDishes.addAll(dishesList);
                calculateTotalAndAmount();
            }

        });


        adapter = new CategoryDishesAdapter(this);
        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(adapter);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        dishesAdapter = new BuffetDishesAdapter(this);
        binding.recView.setAdapter(dishesAdapter);

        binding.btnConfirm.setOnClickListener(v -> {
            /*if (cartDishes.size() > 0) {
                for (DishModel model : cartDishes) {
                    SendOrderModel.Details item = new SendOrderModel.Details( "", model.getId(), "", "", model.getCaterer_id(), model.getAmount() + "", model.getPhoto(), model.getTitel(), model.getPrice());

                    manageCartModel.addItemToCart(this, item,model.getCaterer_id());
                }

                Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                finish();




            }*/


        });

        binding.setDishes(0 + "");
        binding.setTotal(0.0 + "");


        calculateTotalAndAmount();

        mvvm.getDishes(manageCartModel.getDishesList(this));

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
        addUpdateItem(model);
    }

    private void addUpdateItem(DishModel dishModel) {
        DishModel model = new DishModel(dishModel.getId(), dishModel.getCategory_dishes_id(), dishModel.getCaterer_id(), dishModel.getBuffets_id(), dishModel.getFeast_id(), dishModel.getTitel(), dishModel.getPhoto(), dishModel.getPrice(), dishModel.getDetails(), dishModel.getQty(), dishModel.getCreated_at(), dishModel.getUpdated_at(), dishModel.getAmount());
        if (cartDishes.size() > 0) {
            int pos = getItemPos(model.getId());
            if (pos == -1) {
                cartDishes.add(model);
            } else {

                cartDishes.set(pos, model);
            }
        } else {
            cartDishes.add(model);
        }

        calculateTotalAndAmount();


    }

    private void calculateTotalAndAmount() {
        double total = 0;
        int amount = 0;

        for (DishModel model : cartDishes) {
            amount += model.getAmount();
            total += model.getAmount() * Double.parseDouble(model.getPrice());
        }


        binding.setDishes(amount + "");
        binding.setTotal(total + "");


    }

    private int getItemPos(String dish_id) {
        int pos = -1;
        for (int index = 0; index < cartDishes.size(); index++) {
            DishModel model = cartDishes.get(index);
            if (model.getId().equals(dish_id)) {
                pos = index;
                return pos;
            }
        }
        return pos;
    }
}