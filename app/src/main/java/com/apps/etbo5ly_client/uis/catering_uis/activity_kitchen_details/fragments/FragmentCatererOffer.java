package com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.adapters.catering_adapters.KitchenOfferAdapter;
import com.apps.etbo5ly_client.databinding.FragmentCatererOfferBinding;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.OfferModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.KitchenDetailsActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

import java.util.List;

public class FragmentCatererOffer extends BaseFragment {
    private FragmentCatererOfferBinding binding;
    private KitchenDetailsActivity activity;
    private KitchenModel model;
    private KitchenOfferAdapter adapter;
    private ManageCartModel manageCartModel;

    public static FragmentCatererOffer newInstance(KitchenModel model) {
        FragmentCatererOffer fragment = new FragmentCatererOffer();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (KitchenDetailsActivity) context;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_caterer_offer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        manageCartModel = ManageCartModel.newInstance();
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));

        List<OfferModel> offerModelList = model.getOffers();
        for (int index = 0; index < offerModelList.size(); index++) {
            OfferModel model = offerModelList.get(index);
            for (SendOrderModel.Details details : manageCartModel.getDishesList(activity)) {
                if (model.getId().equals(details.getOffer_id())) {
                    model.setAmountInCart(Integer.parseInt(details.getQty()));
                    model.setInCart(true);
                    offerModelList.set(index, model);
                    break;
                }
            }
        }

        binding.recViewLayout.tvNoData.setText(R.string.no_offers);
        if (offerModelList.size() > 0) {
            adapter = new KitchenOfferAdapter(activity, this);
            adapter.updateList(offerModelList);
            binding.recViewLayout.recView.setAdapter(adapter);
            binding.recViewLayout.tvNoData.setVisibility(View.GONE);
        } else {
            binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

        }


        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(false);
            adapter.updateList(model.getOffers());

        });
    }


    public void addToCart(OfferModel model) {
        SendOrderModel.Details item = new SendOrderModel.Details(model.getId(), "", "", "", model.getCaterer_id(), "1", model.getPhoto(), model.getTitle(), model.getPrice(), BaseActivity.OFFER);
        manageCartModel.addItemToCart(activity, item, model.getCaterer_id());
        Toast.makeText(activity, getString(R.string.suc), Toast.LENGTH_SHORT).show();
    }
}
