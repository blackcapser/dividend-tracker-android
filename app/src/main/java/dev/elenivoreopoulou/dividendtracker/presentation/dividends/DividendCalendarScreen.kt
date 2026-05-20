package dev.elenivoreopoulou.dividendtracker.presentation.dividends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividendCalendarScreen() {
    val dividends = listOf(
        "January" to 0,
        "February" to 0,
        "March" to 0,
        "April" to 0,
        "May" to 6706,
        "June" to 0,
        "July" to 0,
        "August" to 0,
        "September" to 6706,
        "October" to 0,
        "November" to 0,
        "December" to 0
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Dividend Calendar",
            style = MaterialTheme.typography.headlineMedium
        )

        dividends.forEach { dividend ->
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = dividend.first,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Expected income: €${dividend.second}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}