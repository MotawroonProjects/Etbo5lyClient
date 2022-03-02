package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.AddressModel;
import com.apps.etbo5ly_client.model.CartOrderModel;
import com.apps.etbo5ly_client.model.CouponDataModel;
import com.apps.etbo5ly_client.model.CouponModel;
import com.apps.etbo5ly_client.model.KitchenModel;
import com.apps.etbo5ly_client.model.OrderModel;
import com.apps.etbo5ly_client.model.SendOrderModel;
import com.apps.etbo5ly_client.model.SingleAddress;
import com.apps.etbo5ly_client.model.SingleKitchenDataModel;
import com.apps.etbo5ly_client.model.SingleOrderDataModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.model.ZoneCover;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentCheckoutMvvm extends AndroidViewModel {
    private static final String TAG = "FragmentCheckMvvm";

    private MutableLiveData<KitchenModel> onDataSuccess;
    private MutableLiveData<CouponModel> onCouponDataSuccess;
    private MutableLiveData<OrderModel> onOrderSuccess;
    private MutableLiveData<AddressModel> onAddressAdded;

    private MutableLiveData<Boolean> isLoadingLivData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentCheckoutMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<KitchenModel> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<CouponModel> getOnCouponDataSuccess() {
        if (onCouponDataSuccess == null) {
            onCouponDataSuccess = new MutableLiveData<>();
        }
        return onCouponDataSuccess;
    }

    public MutableLiveData<OrderModel> getOnOrderSuccess() {
        if (onOrderSuccess == null) {
            onOrderSuccess = new MutableLiveData<>();
        }
        return onOrderSuccess;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }

    public MutableLiveData<AddressModel> onAddressAdded() {
        if (onAddressAdded == null) {
            onAddressAdded = new MutableLiveData<>();
        }
        return onAddressAdded;
    }



    //_________________________hitting api_________________________________

    public void getZone(String kitchen_id) {
        isLoadingLivData.setValue(true);
        Api.getService(Tags.base_url)
                .getCoveredZone(kitchen_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleKitchenDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleKitchenDataModel> response) {
                        isLoadingLivData.setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                onDataSuccess.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                });

    }

    public void checkCoupon(UserModel userModel, String coupon_code, Context context) {
        if (userModel == null) {
            return;
        }
        isLoadingLivData.setValue(true);
        Api.getService(Tags.base_url)
                .checkCoupon(coupon_code, userModel.getData().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CouponDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CouponDataModel> response) {
                        isLoadingLivData.setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnCouponDataSuccess().setValue(response.body().getData());

                                } else if (response.body().getStatus() == 400) {
                                    Toast.makeText(context, R.string.copoun_not_found, Toast.LENGTH_SHORT).show();
                                } else if (response.body().getStatus() == 408) {
                                    Toast.makeText(context, R.string.coupon_used, Toast.LENGTH_SHORT).show();

                                } else if (response.body().getStatus() == 409) {
                                    Toast.makeText(context, R.string.coupon_not_activr, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                        isLoadingLivData.setValue(false);
                    }
                });
    }

    public void sendOrder(SendOrderModel sendOrderModel, Context context) {


        CartOrderModel model = new CartOrderModel(sendOrderModel.getUser_id(), sendOrderModel.getOption_id(), sendOrderModel.getCaterer_id(), sendOrderModel.getTotal(), sendOrderModel.getNotes(), sendOrderModel.getBooking_date(), sendOrderModel.getZone_id(), sendOrderModel.getAddress(), sendOrderModel.getCopon(), sendOrderModel.getPaid_type(), sendOrderModel.getDetails());

        Log.e("user_id", model.getUser_id());
        Log.e("option_id", model.getOption_id());
        Log.e("caterer_id", model.getCaterer_id());
        Log.e("total", model.getTotal());
        Log.e("zone_id", model.getZone_id());
        Log.e("notes", model.getNotes());
        Log.e("booking_date", model.getBooking_date());
        Log.e("copon", model.getCopon());
        Log.e("address", model.getAddress());
        Log.e("paid_type", model.getPaid_type());
        for (SendOrderModel.Details details : model.getDetails()) {
            Log.e("offer_id", details.getOffer_id());
            Log.e("dishes_id", details.getDishes_id());
            Log.e("buffets_id", details.getBuffets_id());
            Log.e("feast_id", details.getFeast_id());
            Log.e("qty", details.getQty());

        }


        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .sendOrder(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOrderDataModel> response) {
                        dialog.dismiss();
                        Log.e("code", response.code() + "__"+response.toString());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.e("codes", response.body().getStatus() + "" );
                                if (response.body().getStatus() == 200) {
                                    getOnOrderSuccess().setValue(response.body().getSingelOrder());

                                } else if (response.body().getStatus() == 409) {
                                    Toast.makeText(context, R.string.cnt_book_time, Toast.LENGTH_SHORT).show();

                                } else if (response.body().getStatus() == 406) {
                                    Toast.makeText(context, R.string.sorry_cnt_make_order, Toast.LENGTH_LONG).show();

                                } else if (response.body().getStatus() == 407) {
                                    Toast.makeText(context, R.string.area_not_covered, Toast.LENGTH_LONG).show();

                                }
                            }
                        } else {
                            try {
                                Log.e("error", response.code() + "" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("Error", e.getMessage() + "__" + e.getLocalizedMessage());
                    }
                });
    }

    public void addAddress(String user_id, String address, String zone_id, String type, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .addAddress(user_id, address, zone_id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleAddress>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleAddress> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    onAddressAdded.setValue(response.body().getData());
                                }
                            }
                        } else {
                            try {
                                Log.e("errorAddAddress", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "Error", e);
                        dialog.dismiss();
                    }
                });


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
