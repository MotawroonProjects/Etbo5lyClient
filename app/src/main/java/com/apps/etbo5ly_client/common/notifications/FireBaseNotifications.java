package com.apps.etbo5ly_client.common.notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
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
import com.apps.etbo5ly_client.model.ChatUserModel;
import com.apps.etbo5ly_client.model.MessageModel;
import com.apps.etbo5ly_client.model.NotiFire;
import com.apps.etbo5ly_client.model.StatusResponse;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_chat.ChatActivity;
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

        for (String key : map.keySet()) {
            Log.e("Key=", key + "_value=" + map.get(key));
        }

        if (getNotificationStatus()) {
            manageNotification(map);
        }

    }

    private void manageNotification(Map<String, String> map) {
        String title = map.get("title");
        String body = map.get("message");
        String notification_type = map.get("noti_type");


        String sound_Path = "";
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        sound_Path = uri.toString();
        Intent cancelIntent = new Intent(this, BroadcastCancelNotification.class);
        PendingIntent cancelPending = PendingIntent.getBroadcast(this, 0, cancelIntent, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setAutoCancel(true)
                .setOngoing(true)
                .setChannelId(App.CHANNEL_ID)
                .setDeleteIntent(cancelPending)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(Uri.parse(sound_Path), AudioManager.STREAM_NOTIFICATION);


        if (notification_type.equals("chat")) {

            String order_id = map.get("order_id");
            String image = map.get("image");

            if (image != null && !image.isEmpty()) {
                body = getString(R.string.attach_sent);
            }


            notificationCompat.setContentTitle(title);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("data", getChatUserModel(map));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));


            if (order_id.equals(getRoomId())) {
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                if (className.equals("com.apps.etbo5ly_client.uis.common_uis.activity_chat.ChatActivity")) {

                    EventBus.getDefault().post(getMessageModel(map));

                } else {


                    if (getChatUserModel(map).getCaterer_image() != null && !getChatUserModel(map).getCaterer_image().isEmpty()) {
                        Log.e("Asdas","33");

                        Glide.with(this)
                                .asBitmap()
                                .load(Uri.parse(Tags.base_url + getChatUserModel(map).getCaterer_image()))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        notificationCompat.setLargeIcon(resource);
                                        manager.notify(Tags.not_id, notificationCompat.build());

                                    }
                                });
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                        notificationCompat.setLargeIcon(bitmap);
                        manager.notify(Tags.not_id, notificationCompat.build());
                    }


                }
            } else {
                if (getChatUserModel(map).getCaterer_image() != null && !getChatUserModel(map).getCaterer_image().isEmpty()) {
                    Glide.with(this)
                            .asBitmap()
                            .load(Uri.parse(Tags.base_url + getChatUserModel(map).getCaterer_image()))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    notificationCompat.setLargeIcon(resource);
                                    manager.notify(Tags.not_id, notificationCompat.build());

                                }
                            });
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                    notificationCompat.setLargeIcon(bitmap);
                    manager.notify(Tags.not_id, notificationCompat.build());
                }

            }

        } else if (notification_type.equals("order")) {
            String order_id = map.get("order_id");
            String from_user_id = map.get("from_user_id");

            UserModel.Data fromUser = new Gson().fromJson(map.get("from_user_data"), UserModel.Data.class);
            UserModel.Data toUser = new Gson().fromJson(map.get("to_user_data"), UserModel.Data.class);

            String caterer_name = "";


            if (from_user_id.equals(getUserModel().getData().getId())) {
                caterer_name = toUser.getName();

            } else {
                caterer_name = fromUser.getName();

            }
            String text = "";
            if (body.equals("approval")) {
                text = getString(R.string.order_approved) + " " + caterer_name + "\n" + getString(R.string.order_num) + " #" + order_id;

            } else if (body.equals("refusal")) {
                text = getString(R.string.order_refused) + " " + caterer_name + "\n" + getString(R.string.order_num) + " #" + order_id;

            } else if (body.equals("making")) {
                text = getString(R.string.your_order_from) + " " + caterer_name + " " + getString(R.string.order_num) + " #" + order_id + "\n" + getString(R.string.status_pending);

            } else if (body.equals("delivery")) {
                text = getString(R.string.your_order_from) + " " + caterer_name + " " + getString(R.string.order_num) + " #" + order_id + "\n" + getString(R.string.delivering);

            } else if (body.equals("completed")) {
                text = getString(R.string.your_order_from) + " " + caterer_name + " " + getString(R.string.order_num) + " #" + order_id + "\n" + getString(R.string.delivered2);

            }
            notificationCompat.setContentTitle(title);
            notificationCompat.setContentText(text);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(text));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("option_id", getOptionId());
            intent.putExtra("firebase", order_id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            notificationCompat.setLargeIcon(bitmap);
            manager.notify(Tags.not_id, notificationCompat.build());
            EventBus.getDefault().post(new NotiFire(true));

        } else {

            notificationCompat.setContentTitle(title);
            notificationCompat.setContentText(body);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("option_id", getOptionId());
            intent.putExtra("firebase", "");

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            notificationCompat.setLargeIcon(bitmap);
            manager.notify(Tags.not_id, notificationCompat.build());
            EventBus.getDefault().post(new NotiFire(true));

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

        String notification_date = map.get("notification_date");
        String room_message_id = map.get("room_message_id");


        UserModel.Data fromUser = new Gson().fromJson(map.get("from_user_data"), UserModel.Data.class);
        UserModel.Data toUser = new Gson().fromJson(map.get("to_user_data"), UserModel.Data.class);


        MessageModel messageModel = new MessageModel(room_message_id, order_id, from_user_id, to_user_id, data_chat_type, message, "", image, "", notification_date, notification_date, fromUser, toUser);

        return messageModel;
    }

    private ChatUserModel getChatUserModel(Map<String, String> map) {
        String order_id = map.get("order_id");
        String user_type = map.get("user_type");
        String from_user_id = map.get("from_user_id");
        String to_user_id = map.get("to_user_id");
        String title = map.get("title");
        String message = map.get("message");
        String image = map.get("image");
        String data_chat_type = map.get("data_chat_type");
        String address = map.get("address");
        String latitude = map.get("latitude");
        String longitude = map.get("longitude");
        String total = String.valueOf(map.get("total"));


        UserModel.Data fromUser = new Gson().fromJson(map.get("from_user_data"), UserModel.Data.class);
        UserModel.Data toUser = new Gson().fromJson(map.get("to_user_data"), UserModel.Data.class);

        String caterer_id = "";
        String caterer_name = "";
        String caterer_phone = "";
        String caterer_image;

        if (from_user_id.equals(getUserModel().getData().getId())) {
            caterer_id = to_user_id;
            caterer_name = toUser.getName();
            caterer_phone = toUser.getPhone_code() + toUser.getPhone();
            caterer_image = toUser.getPhoto();
        } else {
            caterer_id = from_user_id;
            caterer_name = fromUser.getName();
            caterer_phone = fromUser.getPhone_code() + toUser.getPhone();
            caterer_image = fromUser.getPhoto();
        }

        ChatUserModel model = new ChatUserModel(getUserModel().getData().getId(), getUserModel().getData().getName(), getUserModel().getData().getPhone_code() + getUserModel().getData().getPhone(), getUserModel().getData().getPhone(), caterer_id, caterer_name, caterer_phone, caterer_image, address, latitude, longitude, order_id, total);

        return model;

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

    public String getOptionId() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserSettings(this).getOption_id();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
