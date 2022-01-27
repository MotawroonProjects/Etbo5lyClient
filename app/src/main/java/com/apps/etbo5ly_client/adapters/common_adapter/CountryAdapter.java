package com.apps.etbo5ly_client.adapters.common_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.CountryRowBinding;
import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_country.CountryActivity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CountryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private AppCompatActivity activity;

    public CountryAdapter(Context context, String lang) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
        activity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CountryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.country_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);
        myHolder.itemView.setOnClickListener(v -> {
            if (activity instanceof CountryActivity){
                CountryActivity countryActivity = (CountryActivity) activity;
                countryActivity.setItemCountry(list.get(myHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CountryRowBinding binding;

        public MyHolder(CountryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<CountryModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
