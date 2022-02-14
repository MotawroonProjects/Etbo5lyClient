package com.apps.etbo5ly_client.mvvm.mvvm_common;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.SignUpModel;
import com.apps.etbo5ly_client.model.UserModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySignupMvvm extends AndroidViewModel {
    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignupMvvm(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public void signUp(SignUpModel model, Context context) {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody name_part = Common.getRequestBodyText(model.getName());
        RequestBody email_part = Common.getRequestBodyText(model.getEmail());

        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());

        RequestBody lat_part = Common.getRequestBodyText("0.0");
        RequestBody lng_part = Common.getRequestBodyText("0.0");
        RequestBody type_part = Common.getRequestBodyText("client");
        RequestBody software_part = Common.getRequestBodyText("android");


        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(model.getImage()), "photo");
        }


        Api.getService(Tags.base_url).signUp(name_part, phone_code_part, phone_part, email_part, lat_part, lng_part, type_part,software_part, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                } else if (response.body().getStatus() == 404) {
                                    Toast.makeText(context, R.string.ph_em_found, Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    public void update(SignUpModel model, String user_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody user_id_part = Common.getRequestBodyText(user_id);

        RequestBody name_part = Common.getRequestBodyText(model.getName());
        RequestBody email_part = Common.getRequestBodyText(model.getEmail());

        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            if (!model.getImage().startsWith("http")) {
                image = Common.getMultiPart(context, Uri.parse(model.getImage()), "photo");

            }
        }


        Api.getService(Tags.base_url).updateProfile(user_id_part, name_part, email_part, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                } else if (response.body().getStatus() == 404) {
                                    Toast.makeText(context, R.string.ph_em_found, Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
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
