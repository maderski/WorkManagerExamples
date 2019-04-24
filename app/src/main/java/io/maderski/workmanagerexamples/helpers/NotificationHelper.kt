package io.maderski.workmanagerexamples.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationHelper(private val context: Context) {
    fun postNotification(title: String, message: String) {
        val notificationManager: NotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification(
            title,
            message,
            context,
            CHANNEL_ID,
            CHANNEL_NAME,
            android.R.drawable.ic_dialog_info,
            false)
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }

    private fun getNotification(title: String,
                                message: String,
                                context: Context,
                                channelId: String,
                                channelName: String,
                                @DrawableRes icon: Int,
                                isOngoing: Boolean): Notification {
        val builder: NotificationCompat.Builder

        builder = if (Build.VERSION.SDK_INT < 26) {
            NotificationCompat.Builder(context, channelId)
        } else {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = getNotificationChannel(channelId, channelName)
            notificationManager.createNotificationChannel(channel)

            NotificationCompat.Builder(context, channelId)
        }

        val notification = builder
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .build()

        if (isOngoing) {
            notification.flags = NotificationCompat.FLAG_FOREGROUND_SERVICE
        }

        return notification
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(channelId: String, channelName: String): NotificationChannel {
        val notificationChannel = NotificationChannel(channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableVibration(false)
        notificationChannel.setShowBadge(false)
        return notificationChannel
    }

    companion object {
        private const val NOTIFICATION_TAG = "MPSNotification"
        private const val NOTIFICATION_ID = 1112
        private const val CHANNEL_NAME = "Mark Parking Spot"
        private const val CHANNEL_ID = "MPSChannelID"
    }
}