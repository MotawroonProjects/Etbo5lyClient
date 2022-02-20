package com.apps.etbo5ly_client.mvvm.mvvm_common;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.AddressModel;
import com.apps.etbo5ly_client.model.AddressesDataModel;
import com.apps.etbo5ly_client.model.KitchenDataModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityAddressesMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<AddressModel>> onDataSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityAddressesMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<AddressModel>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }



    public void getAddresses(String type, UserModel userModel) {
        if (userModel == null) {
            getIsLoading().setValue(false);
            onDataSuccess().setValue(new ArrayList<>());
            return;
        }
        Log.e("type",type);
        getIsLoading().setValue(true);
        Api.getService(Tags.base_url).getAddresses(userModel.getData().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Observable.fromIterable(response.body().getData())
                                .subscribeOn(Schedulers.io())
                                .filter(addressModel -> {
                                    if (addressModel.getType().equals(type)) {
                                        return true;
                                    }
                                    return false;
                                })
                                .observeOn(AndroidSchedulers.mainThread()).toList();
                    }

                    return null;

                }).subscribe(new SingleObserver<List<AddressModel>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<AddressModel> addressModels) {
                getIsLoading().setValue(false);
                onDataSuccess().setValue(addressModels);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("errorAddress", e.toString());
            }
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
