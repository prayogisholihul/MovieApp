package com.example.MovieApp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder

class Notification{
    fun notif(context: Context) {
        val Notif_Channel = "NotifBeli"
        val notificationManager: NotificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = "TicketNotif"
            val mChannel =
                NotificationChannel(Notif_Channel, channel, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }

        val gotoTicketing = NavDeepLinkBuilder(context).setComponentName(MainMenu::class.java)
            .setGraph(R.navigation.navigation).setDestination(R.id.tiketing)
            .createPendingIntent()


        val builder = NotificationCompat.Builder(context, Notif_Channel)
        builder.setContentIntent(gotoTicketing)
            .setSmallIcon(R.drawable.cinechimp_notif)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.cinechimp_notif))
            .setTicker("Notif Starting")
            .setAutoCancel(true)
            .setContentTitle("Berhasil Membeli")
            .setContentText("Tiket Bioskop sukses terbeli. Klik untuk Detail")
        notificationManager.notify(115, builder.build())
    }
}

