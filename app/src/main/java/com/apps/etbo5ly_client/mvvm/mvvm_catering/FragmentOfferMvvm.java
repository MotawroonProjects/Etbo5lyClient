package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.KitchenDataModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.OfferDataModel;
import com.apps.etbo5ly_client.model.OfferModel;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentOfferMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isOfferLoading;
    private MutableLiveData<List<KitchenModel>> onOfferSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentOfferMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsOfferLoading() {
        if (isOfferLoading == null) {
            isOfferLoading = new MutableLiveData<>();
        }
        return isOfferLoading;
    }

    public MutableLiveData<List<KitchenModel>> onOfferDataSuccess() {
        if (onOfferSuccess == null) {
            onOfferSuccess = new MutableLiveData<>();
        }
        return onOfferSuccess;
    }

    public void getOfferData(String option_id) {
        getIsOfferLoading().setValue(true);
        Api.getService(Tags.base_url).getOffer(option_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<KitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<KitchenDataModel> response) {
                        getIsOfferLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onOfferSuccess.setValue(response.body().getData());
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
