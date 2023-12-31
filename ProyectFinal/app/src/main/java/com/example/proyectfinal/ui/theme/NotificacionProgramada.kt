package com.example.proyectfinal.ui.theme

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.proyectfinal.MainActivity
import com.example.proyectfinal.R
import com.example.proyectfinal.ui.utils.Constants.channelId

class NotificacionProgramada: BroadcastReceiver() {

    companion object{
        const val NOTIFICACION_ID = 5
    }



    override fun onReceive(context: Context?, intent: Intent?) {
        crearNotificacion(context)
    }

    private fun crearNotificacion(context: Context?) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent : PendingIntent
        = PendingIntent.getActivity(context, 0, intent,flag)

        val notificacion = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.information)
            .setContentTitle("Notificacion programada")
            .setContentText("Ejemplo de notificaciones programadas")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Gracias por darnos permisos"+
                        " ahora podemos, notificarte sobre tus tareas y asuntos"
                    )
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICACION_ID, notificacion)
    }
}