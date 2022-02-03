package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.OfferCatererRowBinding;
import com.apps.etbo5ly_client.databinding.OfferRowBinding;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.OfferModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.offer_module.FragmentOffers;
import com.apps.etbo5ly_client.uis.catering_uis.activity_kitchen_details.fragments.FragmentCatererOffer;

import java.util.List;

public class KitchenOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OfferModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private Fragment fragment;

    public KitchenOfferAdapter(Context context, Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        OfferCatererRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.offer_caterer_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.flAddToCart.setOnClickListener(v -> {
            OfferModel offerModel = list.get(myHolder.getAdapterPosition());
            if (fragment instanceof FragmentCatererOffer) {
                if (offerModel.isInCart()) {
                    Toast.makeText(context, context.getString(R.string.already_cart), Toast.LENGTH_SHORT).show();
                } else {
                    offerModel.setInCart(true);
                    offerModel.setAmountInCart(1);
                    myHolder.binding.setModel(offerModel);
                    FragmentCatererOffer fragmentOffers = (FragmentCatererOffer) fragment;
                    fragmentOffers.addToCart(list.get(myHolder.getAdapterPosition()));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private OfferCatererRowBinding binding;

        public MyHolder(OfferCatererRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<OfferModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
