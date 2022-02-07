package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.model.UserSettingsModel;

import io.reactivex.disposables.CompositeDisposable;

public class ActivityHomeGeneralMvvm extends AndroidViewModel {

    private MutableLiveData<UserSettingsModel> onLocationSuccess;
    private MutableLiveData<String> onOptionIdSuccess;
    private MutableLiveData<Boolean> onRefreshSuccess;
    private MutableLiveData<Boolean> onRefreshCartSuccess;
    private MutableLiveData<Boolean> onUserDataRefresh;

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

    public MutableLiveData<Boolean> onUserDateRefresh() {
        if (onUserDataRefresh == null) {
            onUserDataRefresh = new MutableLiveData<>();
        }

        return onUserDataRefresh;
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


}
