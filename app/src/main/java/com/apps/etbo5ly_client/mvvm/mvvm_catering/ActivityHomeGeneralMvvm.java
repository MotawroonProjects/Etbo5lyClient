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
import com.apps.etbo5ly_client.model.AddressModel;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityHomeGeneralMvvm extends AndroidViewModel {

    private MutableLiveData<UserSettingsModel> onLocationSuccess;
    private MutableLiveData<String> onOptionIdSuccess;
    private MutableLiveData<Boolean> onRefreshSuccess;
    private MutableLiveData<Boolean> onRefreshCartSuccess;
    private MutableLiveData<Boolean> onUserDataRefresh;
    private MutableLiveData<OrderModel> onOrderRefresh;
    private MutableLiveData<Boolean> onOrdersRefresh;
    private MutableLiveData<Boolean> onFavoriteRefresh;
    private MutableLiveData<UserModel> onTokenSuccess;
    private MutableLiveData<Boolean> onLoggedOutSuccess;
    private MutableLiveData<AddressModel> onMyAddressSelected;
    private MutableLiveData<Boolean> displayFragmentNotification;
    private MutableLiveData<Boolean> onNotificationRefresh;
    private MutableLiveData<Boolean> onFragmentOrderDetailsRefresh;


    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityHomeGeneralMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserSettingsModel> getLocation() {
        if (onLocationSuccess == null) {
            onLocationSuccess = new MutableLiveData<>();
        }

        return onLocationSuccess;
    }

    public MutableLiveData<String> getOptionId() {
        if (onOptionIdSuccess == null) {
            onOptionIdSuccess = new MutableLiveData<>();
        }

        return onOptionIdSuccess;
    }

    public MutableLiveData<Boolean> onDataRefresh() {
        if (onRefreshSuccess == null) {
            onRefreshSuccess = new MutableLiveData<>();
        }

        return onRefreshSuccess;
    }

    public MutableLiveData<Boolean> onCartRefresh() {
        if (onRefreshCartSuccess == null) {
            onRefreshCartSuccess = new MutableLiveData<>();
        }

        return onRefreshCartSuccess;
    }

    public MutableLiveData<Boolean> onOrdersRefresh() {
        if (onOrdersRefresh == null) {
            onOrdersRefresh = new MutableLiveData<>();
        }

        return onOrdersRefresh;
    }

    public MutableLiveData<Boolean> onUserDateRefresh() {
        if (onUserDataRefresh == null) {
            onUserDataRefresh = new MutableLiveData<>();
        }

        return onUserDataRefresh;
    }

    public MutableLiveData<Boolean> onFavoriteRefresh() {
        if (onFavoriteRefresh == null) {
            onFavoriteRefresh = new MutableLiveData<>();
        }

        return onFavoriteRefresh;
    }


    public MutableLiveData<OrderModel> onOrderRefresh() {
        if (onOrderRefresh == null) {
            onOrderRefresh = new MutableLiveData<>();
        }

        return onOrderRefresh;
    }

    public MutableLiveData<Boolean> onNotificationRefresh() {
        if (onNotificationRefresh == null) {
            onNotificationRefresh = new MutableLiveData<>();
        }

        return onNotificationRefresh;
    }

    public MutableLiveData<Boolean> getOnFragmentOrderDetailsRefresh() {
        if (onFragmentOrderDetailsRefresh == null) {
            onFragmentOrderDetailsRefresh = new MutableLiveData<>();
        }

        return onFragmentOrderDetailsRefresh;
    }


    public MutableLiveData<UserModel> onTokenSuccess() {
        if (onTokenSuccess == null) {
            onTokenSuccess = new MutableLiveData<>();
        }

        return onTokenSuccess;
    }


    public MutableLiveData<Boolean> onLoggedOutSuccess() {
        if (onLoggedOutSuccess == null) {
            onLoggedOutSuccess = new MutableLiveData<>();
        }

        return onLoggedOutSuccess;
    }

    public MutableLiveData<AddressModel> onMyAddressSelected() {
        if (onMyAddressSelected == null) {
            onMyAddressSelected = new MutableLiveData<>();
        }

        return onMyAddressSelected;
    }

    public MutableLiveData<Boolean> getDisplayFragmentNotification() {
        if (displayFragmentNotification == null) {
            displayFragmentNotification = new MutableLiveData<>();
        }

        return displayFragmentNotification;
    }


    public void setOnRefreshSuccess(boolean refresh) {
        onDataRefresh().setValue(refresh);
    }

    public void setOptionId(String option_id) {
        getOptionId().setValue(option_id);
    }

    public void updateLocation(UserSettingsModel model) {
        getLocation().setValue(model);
    }


    public void updateToken(UserModel userModel) {
        if (userModel == null) {
            return;
        }
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Api.getService(Tags.base_url)
                        .updateFireBaseToken(token, userModel.getData().getId(), "android")
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
                                    Log.e("status", response.body().getStatus() + "");
                                    if (response.body() != null) {
                                        if (response.body().getStatus() == 200) {
                                            userModel.setFireBaseToken(token);
                                            onTokenSuccess().setValue(userModel);
                                            Log.e("token", "updated");

                                        }
                                    }

                                } else {
                                    try {
                                        Log.e("error", response.errorBody().string() + "__" + response.code());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("token", e.toString());

                            }
                        });
            }
        });


    }

    public void logout(UserModel userModel, Context context) {
        if (userModel == null) {
            return;
        }
        Log.e("token", userModel.getFireBaseToken());
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .logout(userModel.getData().getId(), userModel.getFireBaseToken())
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
                            Log.e("status", response.body().getStatus() + "");
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    onLoggedOutSuccess().setValue(true);

                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        Log.e("token", e.toString());

                    }
                });


    }


}
