package com.apps.etbo5ly_client.common.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apps.etbo5ly_client.common.tags.Tags;

public class BroadcastCancelNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager != null) {
            manager.cancel(Tags.not_id);
        }
    }
}
