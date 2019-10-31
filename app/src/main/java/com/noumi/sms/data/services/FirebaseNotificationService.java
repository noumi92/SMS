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
    private String mNotificationType;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mNotificationId = (int) System.currentTimeMillis();
        mNotificationTitle = remoteMessage.getNotification().getTitle();
        mNotificationBody = remoteMessage.getNotification().getBody();
        mNotificationClickAction = remoteMessage.getNotification().getClickAction();
        mNotificationType = remoteMessage.getData().get("type");


        Intent resultIntent = getNotificationData(mNotificationType, remoteMessage);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default_channel")
                .setContentTitle(mNotificationTitle)
                .setContentText(mNotificationBody)
                .setSmallIcon(R.mipmap.ic_app_logo);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(resultPendingIntent);

        //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("default_channel", "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //notificationManagerCompat.notify(mNotificationId, builder.build());
        notificationManager.notify(mNotificationId, builder.build());
    }

    private Intent getNotificationData(String notificationType, RemoteMessage remoteMessage) {
        Intent intent = new Intent(mNotificationClickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (notificationType){
            case "tuition_request":{
                String tuitionId = remoteMessage.getData().get("tuitionId");
                intent.putExtra("tuitionId", tuitionId);
            }
            case "chat_requset":{
                String chatId = remoteMessage.getData().get("chatId");
                String studentName = remoteMessage.getData().get("studentName");
                intent.putExtra("chatId", chatId);
                intent.putExtra("senderName", studentName);
            }
        }
        return intent;
    }
}
