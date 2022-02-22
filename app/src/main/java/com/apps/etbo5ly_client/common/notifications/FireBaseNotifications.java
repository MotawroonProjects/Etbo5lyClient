package com.apps.etbo5ly_client.common.notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.preferences.Preferences;
import com.apps.etbo5ly_client.common.remote.Api;
import com.apps.etbo5ly_client.common.share.App;
import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.MessageModel;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class FireBaseNotifications extends FirebaseMessagingService {
    private CompositeDisposable disposable = new CompositeDisposable();
    private Map<String, String> map;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        map = remoteMessage.getData();
        if (getNotificationStatus()) {
            manageNotification(map);
        }

    }

    private void manageNotification(Map<String, String> map) {
        String sound_Path = "";
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        sound_Path = uri.toString();

        String notification_type = map.get("noti_type");
        if (notification_type.equals("chat")) {
            String order_id = map.get("order_id");
            if (order_id.equals(getRoomId())) {
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                if (className.equals("com.apps.etbo5ly_client.uis.common_uis.activity_chat.ChatActivity")) {

                    EventBus.getDefault().post(getMessageModel(map));

                } else {
                    String title = map.get("title");
                    String body = map.get("body");
                    String image = map.get("image");

                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                            .setAutoCancel(true)
                            .setOngoing(true)
                            .setContentTitle(title)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setSound(Uri.parse(sound_Path), AudioManager.STREAM_NOTIFICATION);





                    if (image != null && !image.isEmpty()) {
                        Glide.with(this)
                                .asBitmap()
                                .load(Uri.parse(Tags.base_url + image))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                                    }
                                });
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                        notificationCompat.setLargeIcon(bitmap);
                        manager.notify(1001, notificationCompat.build());
                    }


                }
            } else {


            }

        } else if (notification_type.equals("order")) {
        }

    }


    private MessageModel getMessageModel(Map<String, String> map) {
        String order_id = map.get("order_id");
        String user_type = map.get("user_type");
        String from_user_id = map.get("from_user_id");
        String to_user_id = map.get("to_user_id");
        String title = map.get("title");
        String body = map.get("body");
        String message = map.get("message");
        String image = map.get("image");
        String data_chat_type = map.get("data_chat_type");

        if (image == null) {
            image = "";

        }
        if (data_chat_type.equals("image")) {
            message = getString(R.string.attach_sent);
        }
        String notification_date = map.get("notification_date");
        String room_message_id = map.get("room_message_id");


        UserModel.Data fromUser = new Gson().fromJson(map.get("from_user_data"), UserModel.Data.class);
        UserModel.Data toUser = new Gson().fromJson(map.get("to_user_data"), UserModel.Data.class);


        MessageModel messageModel = new MessageModel(room_message_id, order_id, from_user_id, to_user_id, data_chat_type, message, "", image, "", notification_date, notification_date, fromUser, toUser);

        return messageModel;
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (getUserModel() == null) {
            return;
        }

        Api.getService(Tags.base_url)
                .updateFireBaseToken(s, getUserModel().getData().getId(), "android")
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
                            if (response.body() != null) {
                                Log.e("status", response.body().getStatus() + "");

                                if (response.body().getStatus() == 200) {
                                    UserModel userModel = getUserModel();
                                    userModel.setFireBaseToken(s);
                                    setUserModel(userModel);
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

    public UserModel getUserModel() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserData(this);
    }


    public void setUserModel(UserModel userModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.createUpdateUserData(this, userModel);

    }

    public boolean getNotificationStatus() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserSettings(this).isCanSendNotifications();
    }


    public String getRoomId() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getRoomId(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
