package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.DishBuffetRow2Binding;
import com.apps.etbo5ly_client.databinding.SelectedDishCategoryRowBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_details.BuffetDetailsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_dishes.DishesActivity;

import java.util.List;

public class MenuDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DishModel> list;
    private Context context;
    private LayoutInflater inflater;


    public MenuDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DishBuffetRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.dish_buffet_row2, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private DishBuffetRow2Binding binding;

        public MyHolder(DishBuffetRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<DishModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
