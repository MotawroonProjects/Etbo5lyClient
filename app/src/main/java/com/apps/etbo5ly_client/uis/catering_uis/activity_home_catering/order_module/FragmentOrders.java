package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.order_module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.common_adapter.MyPagerAdapter;
import com.apps.etbo5ly_client.databinding.FragmentOrdersBinding;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();

        fragmentList.add(FragmentCatererCurrentOrder.newInstance());
        fragmentList.add(FragmentCatererPreviousOrder.newInstance());

        titles.add(getString(R.string.current));
        titles.add(getString(R.string.prev));

        adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, titles);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.tab.setupWithViewPager(binding.pager);

    }
}
