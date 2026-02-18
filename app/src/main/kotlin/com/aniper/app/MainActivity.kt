package com.aniper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.aniper.app.ui.navigation.AnIperNavGraph
import com.aniper.app.ui.theme.AnIperTheme
import com.aniper.app.ui.theme.BackgroundDark
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnIperTheme {
                Surface(color = BackgroundDark) {
                    AnIperNavGraph()
                }
            }
        }
    }
}
