package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.common.language.Language;
import com.apps.etbo5ly_client.databinding.ActivityHomeClientBinding;
import com.apps.etbo5ly_client.databinding.ItemMenuCartBinding;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.NotiFire;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.FragmentBaseNavigation;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.navigation.NavigationBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, NavigationBarView.OnItemSelectedListener {
    private ActivityHomeClientBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private Stack<Integer> stack;
    private Map<Integer, Integer> map;
    private String option_id;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private ItemMenuCartBinding itemMenuCartBinding;
    private ManageCartModel manageCartModel;
    private String order_id = "";// is from firebase notification
    private boolean isFromFireBase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_client);
        getDataFromIntent();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manageCartModel.getDishesList(this).size() > 0) {
            updateMenuCartItem(View.VISIBLE);
        } else {
            updateMenuCartItem(View.GONE);

        }
        activityHomeGeneralMvvm.onCartRefresh().setValue(true);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        option_id = intent.getStringExtra("option_id");
        if (intent.hasExtra("firebase")) {
            isFromFireBase = true;
            order_id = intent.getStringExtra("firebase");
        }

    }

    private void initView() {
        manageCartModel = ManageCartModel.newInstance();
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.updateLocation(getUserSettings());
        activityHomeGeneralMvvm.setOptionId(option_id);
        activityHomeGeneralMvvm.onCartRefresh().observe(this, aBoolean -> {
            if (manageCartModel.getDishesList(this).size() > 0) {
                updateMenuCartItem(View.VISIBLE);
            } else {
                updateMenuCartItem(View.GONE);

            }
        });


        activityHomeGeneralMvvm.onLoggedOutSuccess().observe(this, isLoggedOut -> {
            if (isLoggedOut) {
                UserSettingsModel userSettingsModel = getUserSettings();
                userSettingsModel.setCanFinishLogin(false);
                setUserSettings(userSettingsModel);
                clearUserData();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        activityHomeGeneralMvvm.onTokenSuccess().observe(this, this::setUserModel);
        activityHomeGeneralMvvm.getOnServiceChanged().observe(this, isChanged -> {
            setItemPos(0);
        });
        fragmentList = new ArrayList<>();
        stack = new Stack<>();
        map = new HashMap<>();
        map.put(0, R.id.home);
        map.put(1, R.id.offer);
        map.put(2, R.id.cart);
        map.put(3, R.id.order);
        map.put(4, R.id.profile);

        fragmentList.add(FragmentBaseNavigation.newInstance(R.layout.fragment_base_home_navigations, R.id.toolbar, R.id.fragmentBaseHome));
        fragmentList.add(FragmentBaseNavigation.newInstance(R.layout.fragment_base_offer_navigations, R.id.toolbar, R.id.fragmentBaseOffer));
        fragmentList.add(FragmentBaseNavigation.newInstance(R.layout.fragment_base_cart_navigations, R.id.toolbar, R.id.fragmentBaseCart));
        fragmentList.add(FragmentBaseNavigation.newInstance(R.layout.fragment_base_order_navigations, R.id.toolbar, R.id.fragmentBaseOrder));
        fragmentList.add(FragmentBaseNavigation.newInstance(R.layout.fragment_base_profile_navigations, R.id.toolbar, R.id.fragmentBaseProfile));
        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, new ArrayList<>());
        binding.pager.setAdapter(adapter);

        if (stack.isEmpty()) {
            stack.push(0);
        }
        if (isFromFireBase) {
            setItemPos(4);
            activityHomeGeneralMvvm.getDisplayFragmentNotification().setValue(true);
        }
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.pager.addOnPageChangeListener(this);
        binding.bottomNavigation.setOnItemSelectedListener(this);

        itemMenuCartBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_menu_cart, null, false);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) binding.bottomNavigation.getChildAt(0);
        View childAt = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) childAt;
        itemView.addView(itemMenuCartBinding.getRoot());
        updateMenuCartItem(View.GONE);

        activityHomeGeneralMvvm.updateToken(getUserModel());

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewDataRefresh(NotiFire notiFire) {
        activityHomeGeneralMvvm.onNotificationRefresh().setValue(true);
        activityHomeGeneralMvvm.onOrdersRefresh().setValue(true);
        activityHomeGeneralMvvm.getOnFragmentOrderDetailsRefresh().setValue(true);
    }

    public void updateFireBase() {
        activityHomeGeneralMvvm.updateToken(getUserModel());
    }

    private void updateMenuCartItem(int visibility) {

        itemMenuCartBinding.loadView.setVisibility(visibility);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int itemId = map.get(position);
        if (itemId != binding.bottomNavigation.getSelectedItemId()) {
            binding.bottomNavigation.setSelectedItemId(itemId);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int pos = getPos(item.getItemId());
        if (binding.pager.getCurrentItem() != pos) {
            setItemPos(pos);
        }
        return true;
    }

    private void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
        stack.push(pos);
    }

    public void displaySpecificPage(int pos) {
        if (binding.pager.getCurrentItem() != pos) {
            setItemPos(pos);
        }
    }

    private int getPos(int itemId) {
        int position = 0;
        for (int index = 0; index < map.size(); index++) {
            int id = map.get(index);
            if (id == itemId) {
                position = index;
                return position;
            }
        }
        return position;
    }

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }

    @Override
    public void onBackPressed() {

        FragmentBaseNavigation fragmentBaseNavigation = (FragmentBaseNavigation) fragmentList.get(binding.pager.getCurrentItem());
        if (!fragmentBaseNavigation.onBackPressed()) {
            if (stack.size() > 1) {
                stack.pop();
                binding.pager.setCurrentItem(stack.peek());

            } else {

                super.onBackPressed();

            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void logout() {
        activityHomeGeneralMvvm.logout(getUserModel(), this);

    }
}