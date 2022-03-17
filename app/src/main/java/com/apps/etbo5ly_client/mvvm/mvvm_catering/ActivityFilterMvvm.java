package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.FilterModel;
import com.apps.etbo5ly_client.model.KitchenDataModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityFilterMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<KitchenModel>> onDataSuccess;
    private MutableLiveData<Integer> onFavoriteSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityFilterMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<KitchenModel>> onKitchenDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> onAddRemoveFavoriteSuccess() {
        if (onFavoriteSuccess == null) {
            onFavoriteSuccess = new MutableLiveData<>();
        }
        return onFavoriteSuccess;
    }

    public void getData(FilterModel filterModel) {


        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).filterKitchen(filterModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<KitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<KitchenDataModel> response) {
                        Log.e("Emad", response.body().getMessage().toString());

                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getData() != null) {
                                onKitchenDataSuccess().setValue(response.body().getData());
                            }
                        } else {
                            try {
                                Log.e("Error", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Emad", e.getMessage());
                    }
                });
    }


    public void addRemoveFavorite(UserModel userModel, int itemPos) {
        if (userModel == null) {
            return;
        }
        KitchenModel kitchenModel = onDataSuccess.getValue().get(itemPos);

        Api.getService(Tags.base_url).addRemoveFavorite(kitchenModel.getId(), userModel.getData().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                if (kitchenModel.getIs_favorite() == null) {
                                    kitchenModel.setIs_favorite("true");
                                } else if (kitchenModel.getIs_favorite().equals("true")) {
                                    kitchenModel.setIs_favorite("false");

                                } else {
                                    kitchenModel.setIs_favorite("true");

                                }
                                List<KitchenModel> data = onDataSuccess.getValue();
                                data.set(itemPos, kitchenModel);

                                onAddRemoveFavoriteSuccess().setValue(itemPos);

                            }
                        } else {
                            try {
                                Log.e("Error", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Emad", e.getMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
