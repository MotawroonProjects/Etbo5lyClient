package com.apps.etbo5ly_client.uis.catering_uis.activity_filter_setting;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.SelectedCategoryAdapter;
import com.apps.etbo5ly_client.databinding.ActivityFilterSettingBinding;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityFilterSettingMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

import java.util.List;

public class FilterSettingActivity extends BaseActivity {
    private ActivityFilterSettingBinding binding;
    private ActivityFilterSettingMvvm mvvm;
    private FilterModel filterModel;
    private SelectedCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_setting);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        filterModel = (FilterModel) intent.getSerializableExtra("filter");
        if (filterModel == null) {
            filterModel = new FilterModel();
        }
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityFilterSettingMvvm.class);

        mvvm.getIsCategoryDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });

        mvvm.onCategoryDataSuccess().observe(this, categoryList -> {
            if (categoryList.size() > 0) {
                if (adapter != null) {
                    adapter.updateList(categoryList);
                }
            }
        });

        binding.recView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new SelectedCategoryAdapter(this);
        binding.recView.setAdapter(adapter);

        binding.imageClose.setOnClickListener(v -> {
            finish();
        });


        if (filterModel.getIs_delivry().equals("delivry")) {
            binding.rbDelivery.setChecked(true);
        } else if (filterModel.getIs_delivry().equals("not_delivry")) {
            binding.rbReceive.setChecked(true);

        }

        if (filterModel.getIs_type().equals("all")) {
            binding.rbAll.setChecked(true);
        } else if (filterModel.getIs_type().equals("is_special")) {
            binding.rbFeatured.setChecked(true);
        } else if (filterModel.getIs_type().equals("free_delivery")) {
            binding.rbFreeDelivery.setChecked(true);

        } else if (filterModel.getIs_type().equals("most_famous")) {
            binding.rbPopular.setChecked(true);
        }

        if (filterModel.getStart_work().equals("8")) {
            binding.rb1.setChecked(true);
        } else if (filterModel.getStart_work().equals("16")) {
            binding.rb2.setChecked(true);

        } else if (filterModel.getStart_work().equals("22")) {
            binding.rb3.setChecked(true);

        }

        binding.rbDelivery.setOnClickListener(v -> {
            filterModel.setIs_delivry("delivry");
        });

        binding.rbReceive.setOnClickListener(v -> {
            filterModel.setIs_delivry("not_delivry");

        });

        binding.rb1.setOnClickListener(v -> {
            filterModel.setStart_work("8");
            filterModel.setEnd_work("16");
        });

        binding.rb2.setOnClickListener(v -> {
            filterModel.setStart_work("16");
            filterModel.setEnd_work("22");
        });

        binding.rb3.setOnClickListener(v -> {
            filterModel.setStart_work("22");
            filterModel.setEnd_work("8");
        });

        binding.rbAll.setOnClickListener(v -> {
            filterModel.setIs_type("all");
        });

        binding.rbPopular.setOnClickListener(v -> {
            filterModel.setIs_type("most_famous");
        });

        binding.rbFeatured.setOnClickListener(v -> {
            filterModel.setIs_type("is_special");
        });

        binding.rbFreeDelivery.setOnClickListener(v -> {
            filterModel.setIs_type("free_delivery");
        });

        binding.btnApply.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("filter", filterModel);
            setResult(RESULT_OK, intent);
            finish();
        });

        mvvm.getCategoryData(filterModel);
    }

    public void add_remove_item(CategoryModel model) {
        int itemPos = getCategoryIdPos(model);
        List<String> ids = filterModel.getCategory_id();
        if (itemPos != -1) {
            if (model.isSelected()) {
                ids.add(model.getId());
            } else {
                ids.remove(itemPos);
            }
        } else {
            ids.add(model.getId());
        }
        filterModel.setCategory_id(ids);
    }

    private int getCategoryIdPos(CategoryModel model) {
        int pos = -1;
        for (int index = 0; index < filterModel.getCategory_id().size(); index++) {
            String id = filterModel.getCategory_id().get(index);
            if (id.equals(model.getId())) {
                pos = index;
                return pos;
            }
        }

        return pos;
    }
}