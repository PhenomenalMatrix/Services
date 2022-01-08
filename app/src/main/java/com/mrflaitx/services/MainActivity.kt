package com.mrflaitx.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mrflaitx.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.serviceBtn.setOnClickListener {
            // Запуск сервиса
            startService(MyService.newIntent(this,25))
        }

        binding.foregroundServiceBtn.setOnClickListener {
            showNotification()
//            counter++
        }
    }

    private fun showNotification() {
        //За отображение уведомлений отвечает класс NotificationManager
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                //В каком варианте вывести уведомление (со звуком, поверх всех уведомлений и.т.д)
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        // create notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                // Установка заголовка
            .setContentTitle("Title")
            .setContentText("Text")
                // Без этого крашит
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        // Отображение (id нужен для того чтобы не спамилось увидомления)
        notificationManager.notify(1,notification)
//        notificationManager.notify(counter,notification)
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        // Нужен для того чтобы отключать уведомления по нейму
        private const val CHANNEL_NAME = "channel_name"
    }
}