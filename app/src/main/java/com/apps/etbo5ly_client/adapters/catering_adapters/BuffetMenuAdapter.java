package com.apps.etbo5ly_client.adapters.catering_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.BuffetMenuRowBinding;
import com.apps.etbo5ly_client.databinding.BuffetRowBinding;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.uis.catering_uis.activity_buffets.BuffetsActivity;
import com.apps.etbo5ly_client.uis.catering_uis.activity_feasts.FeastsActivity;

import java.util.List;

public class BuffetMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel.Category> list;
    private Context context;
    private LayoutInflater inflater;

    public BuffetMenuAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BuffetMenuRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.buffet_menu_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.recView.setLayoutManager(new GridLayoutManager(context, 2));
        MenuDishesAdapter adapter = new MenuDishesAdapter(context);
        adapter.updateList(list.get(position).getDishes_buffet());
        myHolder.binding.recView.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private BuffetMenuRowBinding binding;

        public MyHolder(BuffetMenuRowBinding binding) {
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
