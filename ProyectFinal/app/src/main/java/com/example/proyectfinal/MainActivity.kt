package com.example.proyectfinal

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.AlarmManagerCompat
import com.example.proyectfinal.navigation.AppNavigation
import com.example.proyectfinal.ui.theme.ProyectFinalTheme
import com.example.proyectfinal.ui.miViewModel
import com.example.proyectfinal.ui.theme.NOTIFICATION_ID
import com.example.proyectfinal.ui.theme.NotificacionProgramada
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.util.Calendar


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val viewModel = miViewModel(context)

            ProyectFinalTheme {
                val windowSize = calculateWindowSizeClass(this)
                Surface {
                    var flag by remember {
                        mutableStateOf(false)
                    }

                    val context = LocalContext.current
                    val idCanal = "CanalMascota"
                    val idNotificacion = 0

                    LaunchedEffect(Unit){
                        crearCanalNotificacion(idCanal, context)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(18.dp)
                            .fillMaxSize()
                    ){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permissionState = rememberPermissionState(
                                permission = android.Manifest.permission.POST_NOTIFICATIONS
                            )
                            if (!permissionState.status.isGranted) {
                                OutlinedButton(onClick = {
                                    permissionState.launchPermissionRequest()
                                }) {
                                    Text(text = "Permitir notificaciones")
                                }
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }

                        Button(onClick = {
                            notificacionesProgramadas(context)
                            flag = true
                        })
                        {
                            Text(text = "Notificaciones")
                        }
                    }

                    if(flag === true){
                        AppNavigation(viewModel, windowSize.widthSizeClass)
                    }
                }
            }
        }
    }



    private fun notificacionesProgramadas(context: Context) {
        val intent = Intent(context, NotificacionProgramada::class.java)
        val pendingIntent  = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Puedes programar alarmas exactas
        AlarmManagerCompat.setExact(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 10000,
            pendingIntent
        )

    }

    private fun crearCanalNotificacion(
        idCanal: String,
        context: Context
    )
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val nombre = "CanalMascota"
            val descripcion = "Canal de notificaciones Mascota"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(idCanal, nombre, importancia)
                .apply{
                    description = descripcion
                }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as
                        NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }
}

/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DefaultPreview(){
    ProyectFinalTheme (darkTheme = false) {
        AppNavigation(
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}
 */
