package com.apps.etbo5ly_client.common.chat_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProviders;

import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.AddChatMessageModel;
import com.apps.etbo5ly_client.model.SingleMessageModel;
import com.apps.etbo5ly_client.mvvm.mvvm_common.ActivityChatMvvm;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ChatService extends Service {
    private AddChatMessageModel model;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            model = (AddChatMessageModel) intent.getSerializableExtra("data");
            sendMessage();
        }
        return START_STICKY;

    }

    public void sendMessage() {

        RequestBody order_id_part = Common.getRequestBodyText(model.getOrder_id());
        RequestBody from_user_id_part = Common.getRequestBodyText(model.getFrom_user_id());
        RequestBody to_user_id_part = Common.getRequestBodyText(model.getTo_user_id());
        RequestBody msg_part = Common.getRequestBodyText(model.getMessage());
        RequestBody msg_type_part = Common.getRequestBodyText(model.getType());
        MultipartBody.Part imagePart = null;

        if (model.getType().equals("image") && model.getImage() != null) {
            imagePart = Common.getMultiPartFromPath(model.getImage(), "image");
        }

        Log.e("data", model.getOrder_id() + "_" + model.getFrom_user_id() + "_" + model.getTo_user_id() + "_" + model.getMessage() + "_" + model.getType() + "_" + model.getType());

        Api.getService(Tags.base_url).sendMessages(order_id_part, from_user_id_part, to_user_id_part, msg_type_part, msg_part, imagePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleMessageModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleMessageModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                EventBus.getDefault().post(response.body().getData());
                                stopSelf();

                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("chatError", e.toString());
                    }
                });

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
