package dev.elenivoreopoulou.dividendtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.elenivoreopoulou.dividendtracker.navigation.DividendTrackerApp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val systemDarkTheme = isSystemInDarkTheme()
            var darkTheme by rememberSaveable { mutableStateOf(systemDarkTheme) }

            DividendTrackerTheme(
                darkTheme = darkTheme,
                onToggleTheme = { darkTheme = !darkTheme }
            ) {
                DividendTrackerApp()
            }
        }
    }
}