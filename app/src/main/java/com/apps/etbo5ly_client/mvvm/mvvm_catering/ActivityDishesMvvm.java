package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.DishModel;
import com.apps.etbo5ly_client.model.DishesDataModel;
import com.apps.etbo5ly_client.model.SendOrderModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityDishesMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BuffetModel.Category>> onDataSuccess;
    private MutableLiveData<List<DishModel>> onDishesCartSuccess;

    private MutableLiveData<Integer> selectedCategoryPos;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityDishesMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<BuffetModel.Category>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> getSelectedCategoryPos() {
        if (selectedCategoryPos == null) {
            selectedCategoryPos = new MutableLiveData<>(-1);
        }

        return selectedCategoryPos;
    }

    public void setSelectedCategoryPos(int pos) {
        getSelectedCategoryPos().setValue(pos);

    }

    public MutableLiveData<List<DishModel>> onDishCartSuccess() {
        if (onDishesCartSuccess == null) {
            onDishesCartSuccess = new MutableLiveData<>();
        }
        return onDishesCartSuccess;
    }

    public void getDishes(List<SendOrderModel.Details> cartDishesList, String kitchen_id) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getCategoryDishes("all", kitchen_id, "dishe")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DishesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<DishesDataModel> response) {
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                updateData(response.body().getData(), cartDishesList);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("error", e.getMessage());
                    }
                });
    }

    private void updateData(List<BuffetModel.Category> data, List<SendOrderModel.Details> cartDishesList) {
        List<DishModel> dishModelList = new ArrayList<>();
        for (int index = 0; index < data.size(); index++) {
            BuffetModel.Category category = data.get(index);
            for (int pos = 0; pos < category.getDishes_buffet().size(); pos++) {
                List<DishModel> dishes_buffet = category.getDishes_buffet();
                DishModel model = dishes_buffet.get(pos);
                for (SendOrderModel.Details details : cartDishesList) {
                    if (details.getDishes_id().equals(model.getId())) {
                        model.setAmount(Integer.parseInt(details.getQty()));
                        dishes_buffet.set(pos, model);
                        category.setDishes_buffet(dishes_buffet);
                        data.set(index, category);
                        dishModelList.add(model);
                        break;
                    }
                }
            }
        }
        onDishCartSuccess().setValue(dishModelList);
        onDataSuccess.setValue(data);


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
