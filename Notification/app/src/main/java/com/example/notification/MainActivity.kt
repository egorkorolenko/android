package com.example.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    companion object{
//        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        var counter = 101
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)
       val vibrate = longArrayOf(1000,1000,1000,1000,1000)


        val button: Button = findViewById(R.id.click)
        button.setOnClickListener {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_pets_24)
                .setContentTitle("Напоминание")
                .setContentText("Пора покормить кота")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setVibrate(vibrate)
                .setColor(Color.BLUE)
                .addAction(R.drawable.ic_baseline_pets_24,"Хорошо",intent)
                .addAction()
                .addAction()

            with(NotificationManagerCompat.from(this)) {
                notify(counter++, builder.build())
            }
        }
    }
}