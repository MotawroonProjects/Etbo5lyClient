package com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.GalleryAdapter;
import com.apps.etbo5ly_client.databinding.FragmentGalleryBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;


public class FragmentGallery extends BaseFragment {
    private FragmentGalleryBinding binding;
    private KitchenModel model;
    private GalleryAdapter adapter;
    private KitchenDetailsActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
    }

    public static FragmentGallery newInstance(KitchenModel model) {
        FragmentGallery fragment = new FragmentGallery();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (model.getPhotos().size() > 0) {
            adapter = new GalleryAdapter(activity);

            adapter.updateList(model.getPhotos());
            binding.recView.setLayoutManager(new GridLayoutManager(activity,3));
            binding.recView.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }
}