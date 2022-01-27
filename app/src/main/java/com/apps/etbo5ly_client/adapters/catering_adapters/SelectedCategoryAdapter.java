package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.SelectedCategoryRowBinding;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_categories.CategoriesActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_filter_setting.FilterSettingActivity;

import java.util.List;

public class SelectedCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;


    public SelectedCategoryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SelectedCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.selected_category_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            CategoryModel model = list.get(myHolder.getAdapterPosition());
            if (model.isSelected()){
                model.setSelected(false);
            }else {
                model.setSelected(true);
            }
            list.set(myHolder.getAdapterPosition(),model);
            notifyItemChanged(myHolder.getAdapterPosition());
            if (appCompatActivity instanceof CategoriesActivity) {
                FilterSettingActivity activity = (FilterSettingActivity) appCompatActivity;
                activity.add_remove_item(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SelectedCategoryRowBinding binding;

        public MyHolder(SelectedCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<CategoryModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
