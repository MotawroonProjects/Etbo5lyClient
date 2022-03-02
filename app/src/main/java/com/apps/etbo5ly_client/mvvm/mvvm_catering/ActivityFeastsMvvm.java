package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.BuffetsDataModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.SendOrderModel;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityFeastsMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BuffetModel>> onDataSuccess;
    private MutableLiveData<Integer> selectedPos = new MutableLiveData<>(-1);

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityFeastsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<BuffetModel>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Integer> getSelectedPos() {
        if (selectedPos == null) {
            selectedPos = new MutableLiveData<>();
        }
        return selectedPos;
    }

    public void getFeasts(String kitchen_id, Context context) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getFeasts(kitchen_id,"client")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<BuffetsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<BuffetsDataModel> response) {
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                                updateData(context, response.body().getData());
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

    private void updateData(Context context, List<BuffetModel> data) {
        ManageCartModel manageCartModel = ManageCartModel.newInstance();

        List<SendOrderModel.Details> detailsList = manageCartModel.getDishesList(context);
        for (int index = 0; index < data.size(); index++) {
            BuffetModel buffetModel = data.get(index);
            for (SendOrderModel.Details details : detailsList) {
                if (buffetModel.getId().equals(details.getFeast_id())) {
                    buffetModel.setInCart(true);
                    buffetModel.setAmountInCart(Integer.parseInt(details.getQty()));
                    data.set(index, buffetModel);
                    break;
                }
            }
        }

        onDataSuccess.setValue(data);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
