package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.home_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CategoryAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.KitchenFeaturedAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.KitchenFreeAdapter;
import com.apps.etbo5ly_client.adapters.catering_adapters.KitchenPopularAdapter;
import com.apps.etbo5ly_client.adapters.common_adapter.SliderAdapter;
import com.apps.etbo5ly_client.databinding.FragmentHomeCateringBinding;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.FragmentHomeCateringMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_categories.CategoriesActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_filter.FilterActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.util.List;

public class FragmentHomeCatering extends BaseFragment {
    private FragmentHomeCateringBinding binding;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private FragmentHomeCateringMvvm mvvm;
    private HomeActivity activity;
    private SliderAdapter sliderAdapter;
    private CategoryAdapter categoryAdapter;
    private KitchenPopularAdapter popularAdapter;
    private KitchenFeaturedAdapter kitchenFeaturedAdapter;
    private KitchenFreeAdapter kitchenFreeAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                getData();
                activityHomeGeneralMvvm.onFavoriteRefresh().setValue(true);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_catering, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(activity).get(FragmentHomeCateringMvvm.class);
        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.getLocation().observe(activity, userSettingModel -> {
            getData();
        });
        activityHomeGeneralMvvm.getOptionId().observe(activity, option_id -> {
            getData();
        });


        mvvm.getIsSliderDataLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loader.startShimmer();
            }
        });

        mvvm.getIsCategoryDataLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loader.startShimmer();
            }
        });

        mvvm.getIsPopularDataLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loader.startShimmer();
            }
        });

        mvvm.getIsFeaturedDataLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loader.startShimmer();

            }
        });

        mvvm.getIsFreeDeliveryDataLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loader.startShimmer();
            } else {
                binding.loader.stopShimmer();
                binding.loader.setVisibility(View.GONE);
                binding.scrollView.setVisibility(View.VISIBLE);

            }
        });


        mvvm.onSliderDataSuccess().observe(activity, sliderList -> {

            if (sliderList.size() > 0) {
                binding.flSlider.setVisibility(View.VISIBLE);


            } else {
                binding.flSlider.setVisibility(View.GONE);

            }

            if (sliderAdapter != null) {
                sliderAdapter.updateList(sliderList);
                binding.pager.setOffscreenPageLimit(sliderList.size());
            }
        });

        mvvm.onCategoryDataSuccess().observe(activity, categoryList -> {
            if (categoryList.size() > 0) {
                binding.llCategories.setVisibility(View.VISIBLE);


            } else {
                binding.llCategories.setVisibility(View.GONE);

            }

            if (categoryAdapter != null) {
                categoryAdapter.updateList(categoryList);
            }
        });

        mvvm.onPopularKitchenDataSuccess().observe(activity, kitchenList -> {
            if (kitchenList.size() > 0) {
                binding.llPopular.setVisibility(View.VISIBLE);


            } else {
                binding.llPopular.setVisibility(View.GONE);

            }

            if (popularAdapter != null) {
                popularAdapter.updateList(kitchenList);
            }
        });

        mvvm.onFeaturedKitchenDataSuccess().observe(activity, kitchenList -> {
            if (kitchenList.size() > 0) {

                binding.llFeaturedKitchens.setVisibility(View.VISIBLE);

            } else {

                binding.llFeaturedKitchens.setVisibility(View.GONE);

            }

            if (kitchenFeaturedAdapter != null) {
                kitchenFeaturedAdapter.updateList(kitchenList);
            }
        });

        mvvm.onFreeKitchenDataSuccess().observe(activity, kitchenList -> {
            if (kitchenList.size() > 0) {


                binding.llFreeDelivery.setVisibility(View.VISIBLE);

            } else {

                binding.llFreeDelivery.setVisibility(View.GONE);

            }

            if (kitchenFreeAdapter != null) {
                kitchenFreeAdapter.updateList(kitchenList);
            }
        });

        activityHomeGeneralMvvm.onFavoriteRefresh().observe(activity, isRefreshed -> {
            getData();
        });

        mvvm.onAddRemoveFavoriteSuccess().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                activityHomeGeneralMvvm.onFavoriteRefresh().setValue(true);
                getData();
            }
        });

        sliderAdapter = new SliderAdapter(activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.tab.setupWithViewPager(binding.pager);

        categoryAdapter = new CategoryAdapter(activity, this);
        binding.recViewCategories.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategories.setAdapter(categoryAdapter);


        popularAdapter = new KitchenPopularAdapter(activity, this);
        binding.recViewPopular.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewPopular.setAdapter(popularAdapter);


        kitchenFeaturedAdapter = new KitchenFeaturedAdapter(activity, this);
        binding.recViewFeaturedKitchens.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewFeaturedKitchens.setAdapter(kitchenFeaturedAdapter);

        kitchenFreeAdapter = new KitchenFreeAdapter(activity, this);
        binding.recViewFreeDelivery.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewFreeDelivery.setAdapter(kitchenFreeAdapter);


        binding.tvMoreCategories.setOnClickListener(v -> {
            Intent intent = new Intent(activity, CategoriesActivity.class);
            startActivity(intent);
        });

        binding.btnAll.setOnClickListener(v -> {
            navigateToFilterActivity("all", "");
        });

        binding.tvMorePopular.setOnClickListener(v -> {
            navigateToFilterActivity("most_famous", "");
        });

        binding.tvMoreFeaturedKitchens.setOnClickListener(v -> {
            navigateToFilterActivity("is_special", "");

        });

        binding.tvMoreFreeDelivery.setOnClickListener(v -> {
            navigateToFilterActivity("free_delivery", "");

        });

    }

    private void getData() {
        binding.scrollView.setVisibility(View.GONE);
        binding.loader.startShimmer();
        binding.loader.setVisibility(View.VISIBLE);

        String lat = getUserSettings().getLocation().getLat() + "";
        String lng = getUserSettings().getLocation().getLng() + "";
        mvvm.getSliderData();
        mvvm.getCategoryData();
        String user_id = null;
        if (getUserModel() != null) {
            user_id = getUserModel().getData().getId();
        }
        mvvm.getPopularKitchenData(user_id, lat, lng, activityHomeGeneralMvvm.getOptionId().getValue());
        mvvm.getFeaturedKitchenData(user_id, lat, lng, activityHomeGeneralMvvm.getOptionId().getValue());
        mvvm.getFreeKitchenData(user_id, lat, lng, activityHomeGeneralMvvm.getOptionId().getValue());

    }

    public void setItemCategory(CategoryModel categoryModel) {
        navigateToFilterActivity("all", categoryModel.getId());
    }


    public void setItemKitchen(KitchenModel kitchenModel) {
        req = 1;
        Intent intent = new Intent(activity, KitchenDetailsActivity.class);
        intent.putExtra("kitchen_id", kitchenModel.getId());
        launcher.launch(intent);
    }

    private void navigateToFilterActivity(String type, String category_id) {
        req = 1;
        FilterModel filterModel = new FilterModel();
        filterModel.setLatitude(getUserSettings().getLocation().getLat() + "");
        filterModel.setLongitude(getUserSettings().getLocation().getLng() + "");

        if (getUserModel() != null) {
            filterModel.setUser_id(getUserModel().getData().getId());
        }
        if (!category_id.isEmpty()) {
            List<String> ids = filterModel.getCategory_id();
            ids.add(category_id);
            filterModel.setCategory_id(ids);
        }

        filterModel.setIs_type(type);

        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra("filter", filterModel);
        launcher.launch(intent);
    }

    public void addRemoveFavorite(String kitchen_id) {
        mvvm.addRemoveFavorite(getUserModel(), kitchen_id, getUserSettings().getLocation().getLat() + "", getUserSettings().getLocation().getLng() + "", getUserSettings().getOption_id());

    }
}
