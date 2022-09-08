package com.example.FirebaseWork.hamza

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.FirebaseWork.R
import com.example.FirebaseWork.databinding.ActivityHamzaMainBinding

class HamzaMainActivity : AppCompatActivity() {
    private var  CHANNALID = "Your_channel_id"

    private lateinit var binding: ActivityHamzaMainBinding
    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHamzaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotifitionChannel()

        val notificationLayout = RemoteViews(packageName,R.layout.hamza_notification2)
        var builder = NotificationCompat.Builder(this,CHANNALID)
            .setContentTitle("your Title")
            .setSmallIcon(R.drawable.google)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        binding.notification1.setOnClickListener {

            with(NotificationManagerCompat.from(this)){
                notify(0,builder.build())
            }



        }





    }


    private fun createNotifitionChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "App Notification"
            val descriptionText = "This is your notification description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel =
                NotificationChannel(CHANNALID, name, importance).apply {
                    description = descriptionText
                    Log.d("tag", "Create notification2")
                }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("tag", "Create notification1")

        }
    }

}