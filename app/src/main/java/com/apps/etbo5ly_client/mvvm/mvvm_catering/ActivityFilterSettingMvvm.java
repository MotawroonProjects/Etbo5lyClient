package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.CategoryDataModel;
import com.apps.etbo5ly_client.model.CategoryModel;
import com.apps.etbo5ly_client.model.FilterModel;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityFilterSettingMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isCategoryDataLoading;
    private MutableLiveData<List<CategoryModel>> onCategorySuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityFilterSettingMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsCategoryDataLoading() {
        if (isCategoryDataLoading == null) {
            isCategoryDataLoading = new MutableLiveData<>();
        }
        return isCategoryDataLoading;
    }

    public MutableLiveData<List<CategoryModel>> onCategoryDataSuccess() {
        if (onCategorySuccess == null) {
            onCategorySuccess = new MutableLiveData<>();
        }
        return onCategorySuccess;
    }

    public void getCategoryData(FilterModel filterModel) {
        getIsCategoryDataLoading().setValue(true);
        Api.getService(Tags.base_url).getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        getIsCategoryDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getData() != null) {
                                updateData(response.body().getData(), filterModel);

                            }
                        } else {
                            try {
                                Log.e("slideError", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }

    private void updateData(List<CategoryModel> data, FilterModel filterModel) {
        for (int index = 0; index < data.size(); index++) {
            CategoryModel categoryModel = data.get(index);
            if (isSelected(filterModel, categoryModel.getId())) {
                categoryModel.setSelected(true);
                data.set(index, categoryModel);
            }
        }

        onCategorySuccess.setValue(data);

    }

    private boolean isSelected(FilterModel filterModel, String category_id) {
        for (String id : filterModel.getCategory_id()) {
            if (category_id.equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
