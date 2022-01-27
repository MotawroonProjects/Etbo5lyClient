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
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getData() != null) {
                                onKitchenDataSuccess().setValue(response.body().getData());
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


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
