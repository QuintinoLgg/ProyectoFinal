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
import com.example.proyectfinal.ui.theme.AndroidAudioPlayer
import com.example.proyectfinal.ui.theme.AndroidAudioRecorder
import com.example.proyectfinal.ui.theme.GrabarAudioScreen
import com.example.proyectfinal.ui.theme.NOTIFICATION_ID
import com.example.proyectfinal.ui.theme.NotificacionProgramada
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.Calendar


class MainActivity : ComponentActivity() {

    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null

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
                    AppNavigation(viewModel, windowSize.widthSizeClass)
                    GrabarAudioScreen(
                        onClickStGra = {
                            File(cacheDir, "audio.mp3").also {
                                recorder.start(it)
                                audioFile = it
                            }

                        },
                        onClickSpGra = {recorder.stop()},
                        onClickStRe = { audioFile?.let { player.start(it) } },
                        onClickSpRe = {player.stop()}
                    )
                }
            }
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
