package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.MainCategoryRowBinding;
import com.apps.etbo5ly_client.databinding.SelectedDishCategoryRowBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffet_dishes.BuffetDishesActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.home_module.FragmentHomeCatering;

import java.util.List;

public class CategoryDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel.Category> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;
    private int currentPos = 0;
    private int oldPos = currentPos;

    public CategoryDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SelectedDishCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.selected_dish_category_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            if (oldPos != -1) {
                BuffetModel.Category oldCategory = list.get(oldPos);
                oldCategory.setSelected(false);
                list.set(oldPos, oldCategory);
                notifyItemChanged(oldPos);
            }
            currentPos = myHolder.getAbsoluteAdapterPosition();
            BuffetModel.Category category = list.get(currentPos);
            category.setSelected(true);
            list.set(currentPos, category);
            notifyItemChanged(currentPos);
            oldPos = currentPos;


            if (appCompatActivity instanceof BuffetDishesActivity) {
                BuffetDishesActivity activity = (BuffetDishesActivity) appCompatActivity;
                activity.setItemCategory(category, currentPos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SelectedDishCategoryRowBinding binding;

        public MyHolder(SelectedDishCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<BuffetModel.Category> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
