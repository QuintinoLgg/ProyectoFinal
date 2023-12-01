package com.example.proyectfinal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.platform.LocalContext
import com.example.proyectfinal.navigation.AppNavigation
import com.example.proyectfinal.ui.theme.ProyectFinalTheme
import com.example.proyectfinal.ui.miViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
