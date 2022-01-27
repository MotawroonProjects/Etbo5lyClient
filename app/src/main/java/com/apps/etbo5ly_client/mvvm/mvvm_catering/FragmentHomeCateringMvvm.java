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

public class FragmentHomeCateringMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isSliderDataLoading;
    private MutableLiveData<Boolean> isCategoryDataLoading;
    private MutableLiveData<Boolean> isPopularDataLoading;
    private MutableLiveData<Boolean> isFeaturedDataLoading;
    private MutableLiveData<Boolean> isFreeDeliveryDataLoading;


    private MutableLiveData<List<CategoryModel>> onSliderSuccess;
    private MutableLiveData<List<CategoryModel>> onCategorySuccess;
    private MutableLiveData<List<KitchenModel>> onPopularKitchenSuccess, onFeaturedKitchenSuccess, onFreeKitchenSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentHomeCateringMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsSliderDataLoading() {
        if (isSliderDataLoading == null) {
            isSliderDataLoading = new MutableLiveData<>();
        }
        return isSliderDataLoading;
    }

    public MutableLiveData<Boolean> getIsCategoryDataLoading() {
        if (isCategoryDataLoading == null) {
            isCategoryDataLoading = new MutableLiveData<>();
        }
        return isCategoryDataLoading;
    }

    public MutableLiveData<Boolean> getIsPopularDataLoading() {
        if (isPopularDataLoading == null) {
            isPopularDataLoading = new MutableLiveData<>();
        }
        return isPopularDataLoading;
    }

    public MutableLiveData<Boolean> getIsFeaturedDataLoading() {
        if (isFeaturedDataLoading == null) {
            isFeaturedDataLoading = new MutableLiveData<>();
        }
        return isFeaturedDataLoading;
    }

    public MutableLiveData<Boolean> getIsFreeDeliveryDataLoading() {
        if (isFreeDeliveryDataLoading == null) {
            isFreeDeliveryDataLoading = new MutableLiveData<>();
        }
        return isFreeDeliveryDataLoading;
    }

    public MutableLiveData<List<CategoryModel>> onSliderDataSuccess() {
        if (onSliderSuccess == null) {
            onSliderSuccess = new MutableLiveData<>();
        }
        return onSliderSuccess;
    }


    public MutableLiveData<List<CategoryModel>> onCategoryDataSuccess() {
        if (onCategorySuccess == null) {
            onCategorySuccess = new MutableLiveData<>();
        }
        return onCategorySuccess;
    }

    public MutableLiveData<List<KitchenModel>> onPopularKitchenDataSuccess() {
        if (onPopularKitchenSuccess == null) {
            onPopularKitchenSuccess = new MutableLiveData<>();
        }
        return onPopularKitchenSuccess;
    }

    public MutableLiveData<List<KitchenModel>> onFeaturedKitchenDataSuccess() {
        if (onFeaturedKitchenSuccess == null) {
            onFeaturedKitchenSuccess = new MutableLiveData<>();
        }
        return onFeaturedKitchenSuccess;
    }

    public MutableLiveData<List<KitchenModel>> onFreeKitchenDataSuccess() {
        if (onFreeKitchenSuccess == null) {
            onFreeKitchenSuccess = new MutableLiveData<>();
        }
        return onFreeKitchenSuccess;
    }


    public void getSliderData() {
        getIsSliderDataLoading().setValue(true);
        Api.getService(Tags.base_url).getMainSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        getIsSliderDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null &&response.body().getStatus()==200&& response.body().getData() != null) {
                                onSliderSuccess.setValue(response.body().getData());
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
                            if (response.body() != null && response.body().getData() != null) {
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

    public void getPopularKitchenData(String user_id,String lat, String lng, String option_id) {
        getIsPopularDataLoading().setValue(true);
        Api.getService(Tags.base_url).getPopularKitchen(user_id,lat, lng, option_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<KitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<KitchenDataModel> response) {
                        getIsPopularDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null &&response.body().getStatus()==200&& response.body().getData() != null) {
                                onPopularKitchenSuccess.setValue(response.body().getData());
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
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void getFeaturedKitchenData(String user_id,String lat, String lng, String option_id) {
        getIsFeaturedDataLoading().setValue(true);
        Api.getService(Tags.base_url).getFeaturedKitchen(user_id,lat, lng, option_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<KitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<KitchenDataModel> response) {
                        getIsFeaturedDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null &&response.body().getStatus()==200&& response.body().getData() != null) {
                                onFeaturedKitchenSuccess.setValue(response.body().getData());
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
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public void getFreeKitchenData(String user_id,String lat, String lng, String option_id) {
        getIsFreeDeliveryDataLoading().setValue(true);
        Api.getService(Tags.base_url).getFreeDeliveryKitchen(user_id,lat, lng, option_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<KitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<KitchenDataModel> response) {
                        getIsFreeDeliveryDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null &&response.body().getStatus()==200&& response.body().getData() != null) {
                                onFreeKitchenSuccess.setValue(response.body().getData());
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
