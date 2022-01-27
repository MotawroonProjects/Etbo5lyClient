package com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.CommentAdapter;
import com.apps.etbo5ly_client.databinding.FragmentCommentsBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;


public class FragmentComments extends BaseFragment {
    private FragmentCommentsBinding binding;
    private KitchenModel model;
    private CommentAdapter adapter;
    private KitchenDetailsActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
    }

    public static FragmentComments newInstance(KitchenModel model) {
        FragmentComments fragment = new FragmentComments();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = (KitchenModel) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (model.getRate().size() > 0) {
            adapter = new CommentAdapter(activity);
            adapter.updateList(model.getRate());
            binding.recView.setLayoutManager(new LinearLayoutManager(activity));
            binding.recView.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

    }
}