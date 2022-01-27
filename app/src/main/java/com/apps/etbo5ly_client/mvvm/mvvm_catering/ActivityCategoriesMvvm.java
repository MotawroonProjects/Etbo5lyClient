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

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityCategoriesMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isCategoryDataLoading;
    private MutableLiveData<List<CategoryModel>> onCategorySuccess;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityCategoriesMvvm(@NonNull Application application) {
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

    public void getCategoryData() {
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
                            if (response.body() != null &&response.body().getStatus()==200&& response.body().getData() != null) {
                                onCategorySuccess.setValue(response.body().getData());
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
