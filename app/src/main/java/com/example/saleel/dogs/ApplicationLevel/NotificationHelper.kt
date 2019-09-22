package com.example.saleel.dogs.ApplicationLevel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.saleel.dogs.R
import com.example.saleel.dogs.view.MainActivity
import kotlinx.android.synthetic.main.item_layout.view.*

class NotificationHelper(var context: Context) {

    private var CHANNEL_ID:String ="Dogs PN"
    private var NOTIFICATION_ID :Int =16
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name:String=CHANNEL_ID
            var description:String="Channel description"
            val importance:Int=NotificationManager.IMPORTANCE_DEFAULT
            val channel :NotificationChannel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                this.description = description
            }
            val notificationManager:NotificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    public fun createNotification(){
        createNotificationChannel()
        val intent:Intent = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(context,0,intent,0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog)
        val notification =NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.dog_pf_icon)
            .setLargeIcon(icon)
            .setContentTitle("Dogs Data Retrived")
            .setContentText("This notification has some content")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,notification)


    }
}