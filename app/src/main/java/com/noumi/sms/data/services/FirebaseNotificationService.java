package com.noumi.sms.data.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.noumi.sms.R;

public class FirebaseNotificationService extends FirebaseMessagingService {
    private String TAG = "com.noumi.sms.custom.log";
    private int mNotificationId;
    private String mNotificationTitle;
    private String mNotificationBody;
    private String mNotificationClickAction;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mNotificationId = (int) System.currentTimeMillis();
        mNotificationTitle = remoteMessage.getNotification().getTitle();
        mNotificationBody = remoteMessage.getNotification().getBody();
        mNotificationClickAction = remoteMessage.getNotification().getClickAction();

        Intent intent = new Intent(mNotificationClickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"ok")
                .setContentTitle(mNotificationTitle)
                .setContentText(mNotificationBody)
                .setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.wecome_text), "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(mNotificationId, builder.build());
    }
}
