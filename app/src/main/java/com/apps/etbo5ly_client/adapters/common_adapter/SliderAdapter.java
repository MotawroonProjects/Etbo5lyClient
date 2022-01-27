package com.apps.etbo5ly_client.adapters.common_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.SliderBinding;
import com.apps.etbo5ly_client.model.CategoryModel;

import java.util.List;

public class SliderAdapter extends PagerAdapter {


    private List<CategoryModel> list;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter( Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        SliderBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.slider, view, false);
        rowBinding.setImage(list.get(position).getPhoto());
        view.addView(rowBinding.getRoot());
        return rowBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void updateList(List<CategoryModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}