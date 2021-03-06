package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.BuffetModel;
import com.apps.etbo5ly_client.model.BuffetsDataModel;
import com.apps.etbo5ly_client.model.ManageCartModel;
import com.apps.etbo5ly_client.model.OrderDataModel;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentCatererCurrentOrderMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<OrderModel>> onDataSuccess;
    private MutableLiveData<Boolean> onResendSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentCatererCurrentOrderMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<List<OrderModel>> onDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Boolean> onResendSuccess() {
        if (onResendSuccess == null) {
            onResendSuccess = new MutableLiveData<>();
        }
        return onResendSuccess;
    }


    public void getOrders(UserModel userModel) {
        if (userModel == null) {
            getIsDataLoading().setValue(false);
            onDataSuccess.setValue(new ArrayList<>());

            return;
        }
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getCatererOrder(userModel.getData().getId(), "new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OrderDataModel> response) {
                        getIsDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getMyOrder() != null) {
                                onDataSuccess.setValue(response.body().getMyOrder());

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

    public void resendOrder(Context context, String order_id) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).reSendOrder(order_id, "new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onResendSuccess().setValue(true);

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.getMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
