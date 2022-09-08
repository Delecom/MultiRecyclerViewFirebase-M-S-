package com.example.FirebaseWork

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso
import java.util.concurrent.atomic.AtomicInteger


class FirebaseMessageReceiver : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i(TAG, "onMessageReceived: ")

        //Data available
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val shortDesc = remoteMessage.data["short_desc"]
        val image = remoteMessage.data["feature"]
        val packageName = remoteMessage.data["package"]
        Handler(this.mainLooper).post {
                    sendNotification(icon!!, title!!, shortDesc!!, image, packageName!!)
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
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(storePackage))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val remoteViews = RemoteViews(packageName, R.layout.nofication_layout)
        remoteViews.setTextViewText(R.id.tv_title, title)
        remoteViews.setTextViewText(R.id.tv_short_desc, shortDesc)

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
        val notificationID = getNextInt()
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



    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private val seed = AtomicInteger()
        fun getNextInt(): Int {
            return seed.incrementAndGet()
        }
    }
}