package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.databinding.ActivityHomeClientBinding;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.FragmentBaseNavigation;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, NavigationBarView.OnItemSelectedListener {
    private ActivityHomeClientBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private Stack<Integer> stack;
    private Map<Integer, Integer> map;
    private String option_id;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;

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
        activityHomeGeneralMvvm.onCartRefresh().setValue(true);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        option_id = intent.getStringExtra("option_id");

    }

    private void initView() {
        activityHomeGeneralMvvm = ViewModelProviders.of(this).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.updateLocation(getUserSettings());
        activityHomeGeneralMvvm.setOptionId(option_id);

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
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.pager.addOnPageChangeListener(this);
        binding.bottomNavigation.setOnItemSelectedListener(this);
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
}