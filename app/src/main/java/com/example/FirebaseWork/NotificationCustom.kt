package com.example.FirebaseWork

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso


class NotificationCustom : AppCompatActivity() {

    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_custom)
        actionBar?.hide()
        FirebaseMessaging.getInstance().subscribeToTopic("HamzaBinAbdulMannan")

        val btn_Notification = findViewById<Button>(R.id.btn_Notification)
        btn_Notification.setOnClickListener {
            sendNotification(
                "https://play-lh.googleusercontent.com/iUUDxhI23mMaiDB4eiKoNI6g2XTX2-JwNTlsnTJ5eskKF7PLPOuNiliBXqPRpekgcQ=s360-rw",
                "Live Earth Map-Street View Map",
                "Live Earth Map is an advanced street view app for the person who remains busy in daily life, and can't have enough time to explore the world",
                "https://play-lh.googleusercontent.com/ArJ_wvqt6-jAb7PozRpWraHWcqVCXatVpjAq0mMf7aORpSmLSDDnXa0-riNGXqmkyw=w1440-h620-rw",
                "https://play.google.com/store/apps/details?id=com.live.earth.maps.liveearth.livelocations.mylocation.streetview.maps2019")
        }
    }

    private fun sendNotification(
        icon: String,
        title: String,
        shortDesc: String,
        image: String?,
        storePackage: String,
    ) {

        //Open PlayStore
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$storePackage"))

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        //Make Remote Views For text
        val remoteViews = RemoteViews(packageName, R.layout.nofication_layout)
        remoteViews.setTextViewText(R.id.tv_title, title)
        remoteViews.setTextViewText(R.id.tv_short_desc, shortDesc)

        //Notification Parameters
        val channelId = "mahboobKhan"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.instagram)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Promotion Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        //Build Notification
        val notificationID = FirebaseMessageReceiver.getNextInt()
        notificationManager.notify(notificationID, notificationBuilder.build())

        //Set Images into remoteViews
        Picasso.get().load(icon)
            .into(remoteViews, R.id.iv_icon, notificationID, notificationBuilder.build())
        if (image != null) {
            remoteViews.setViewVisibility(R.id.iv_feature, View.VISIBLE)
            Picasso.get().load(image)
                .into(remoteViews, R.id.iv_feature, notificationID, notificationBuilder.build())
        }
    }

}