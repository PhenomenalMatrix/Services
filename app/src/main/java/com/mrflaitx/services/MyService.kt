package com.mrflaitx.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService: Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStart")
        coroutineScope.launch {
            for (i in 0 until 100){
             delay(1000)
                log("Timer $i")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    // Вывод сообщение в лог
    private fun log(message:String){
        Log.e("SERVICE_TAG", "MyService: $message ")
    }

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context,MyService::class.java)
        }
    }

    //Сервисы предназначенны для выполнения задач на фоне
    // Чтобы создать сервис надо унаследоваться от Сервисе
    //ЖЦ сервисов: onCreate(сервис создан) -onStartCommand(сервис выполняет код) -onDestroy(сервис уничтожен)
    //Поумолчанию код внутри сервисов выполняется на главном потоке
    //Service должны быть зарегист в манифесте
    //Чтобы запустить сервис, нужно вызвать метод startService в активити и передать Intent в качестве параметра
}