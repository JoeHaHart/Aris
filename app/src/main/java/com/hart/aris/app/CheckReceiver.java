package com.hart.aris.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CheckReceiver extends BroadcastReceiver {
    public CheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle bundle = intent.getExtras();
            String studyType = bundle.getString("study_type");
            String name = bundle.getString("name");
            String classType = bundle.getString("activity_type");
            String className = classType.substring(classType.lastIndexOf(" ") + 1);
            Class c = Class.forName(className);
            String text = "How's the " + studyType + " going?";
            createNotification(c, context, name, text);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void createNotification(Class activity, Context context, String name, String text) {
        // Prepare intent which is triggered if the
        int requestID = (int) System.currentTimeMillis();
        // notification is selected
        Log.e("In noti", activity.toString());
        Intent intent = new Intent(context, activity);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Hi " + name + ". Can we chat?")
                .setContentText(text).setSmallIcon(R.drawable.ic_launcher)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000 })
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }
}
