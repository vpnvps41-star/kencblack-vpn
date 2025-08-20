package com.kencblack.vpn

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class KencVpnService : VpnService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("action")
        if (action == "start") {
            startForegroundServiceWithNotification()
            // Aquí integrarías la lógica para arrancar WireGuard (invocando la API del módulo wireguard-android
            // o ejecutando el binario wireguard-go embebido). Por ahora es un placeholder.
        } else if (action == "stop") {
            stopForeground(true)
            stopSelf()
        }
        return Service.START_STICKY
    }

    private fun startForegroundServiceWithNotification() {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "kencblack_vpn"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel(channelId, "kencblack VPN", NotificationManager.IMPORTANCE_LOW))
        }
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("kencblack VPN")
            .setContentText("Túnel iniciando...")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
