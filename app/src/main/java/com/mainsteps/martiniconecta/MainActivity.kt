package com.mainsteps.martiniconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mainsteps.martiniconecta.navigation.AppNavigation
import com.mainsteps.martiniconecta.ui.theme.MartiniConecta_Grupo15Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MartiniConecta_Grupo15Theme {
                AppNavigation(
                    hasProfile = false
                )
            }
        }
    }
}