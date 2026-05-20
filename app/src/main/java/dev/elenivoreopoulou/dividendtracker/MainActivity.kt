package dev.elenivoreopoulou.dividendtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.elenivoreopoulou.dividendtracker.navigation.DividendTrackerApp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DividendTrackerTheme {
                DividendTrackerApp()
            }
        }
    }
}