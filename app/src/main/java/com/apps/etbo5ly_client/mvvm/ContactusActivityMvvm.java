package com.apps.etbo5ly_client.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.model.ContactUsModel;

import io.reactivex.disposables.CompositeDisposable;

public class ContactusActivityMvvm extends AndroidViewModel {
    private Context context;

    public MutableLiveData<Boolean> send = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ContactusActivityMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }

    public void contactus(Context context, ContactUsModel contactUsModel) {
       /* ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).contactUs(Tags.api_key, contactUsModel.getName(), contactUsModel.getEmail(), contactUsModel.getSubject(), contactUsModel.getMessage()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Response<StatusResponse> statusResponseResponse) {
                dialog.dismiss();
                if (statusResponseResponse.isSuccessful()) {
                    if (statusResponseResponse.body().getStatus() == 200) {
                        send.postValue(true);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                dialog.dismiss();
            }
        });*/


    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
