package com.anjelitahp0044.assessment3_mobpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.anjelitahp0044.assessment3_mobpro.screen.MainScreen
import com.anjelitahp0044.assessment3_mobpro.ui.theme.Assessment3_MobproTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assessment3_MobproTheme {
                MainScreen()
            }
        }

    }
}