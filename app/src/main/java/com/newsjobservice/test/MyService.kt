package com.newsjobservice.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.newsjobservice.R

class MyService : Service() {
   private lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString()->{
               startMediaPlayerService()
            }
            Actions.STOP.toString()->{
                stopSelf()
                mediaPlayer.stop()

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startMediaPlayerService() {
        mediaPlayer = MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        mediaPlayer.start()
        mediaPlayer.isLooping=true

        val notification = NotificationCompat.Builder(this, "bell_channel")
            .setContentTitle("Bell Ringing...")
            .setContentText("Bell ton ton")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()

        startForeground(1001,notification)
    }

   /* private fun showNotification(title: String, content: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "bell_channel",
                "Bell Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "bell_channel")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }*/

}



enum class Actions{
    START,STOP
}