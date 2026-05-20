package dev.elenivoreopoulou.dividendtracker.presentation.addholding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddHoldingScreen() {
    val companyName = remember { mutableStateOf("") }
    val ticker = remember { mutableStateOf("") }
    val shares = remember { mutableStateOf("") }
    val averagePrice = remember { mutableStateOf("") }
    val dividendPerShare = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Add Holding",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = companyName.value,
            onValueChange = { companyName.value = it },
            label = { Text("Company name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ticker.value,
            onValueChange = { ticker.value = it },
            label = { Text("Ticker") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = shares.value,
            onValueChange = { shares.value = it },
            label = { Text("Shares") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = averagePrice.value,
            onValueChange = { averagePrice.value = it },
            label = { Text("Average buy price") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dividendPerShare.value,
            onValueChange = { dividendPerShare.value = it },
            label = { Text("Annual dividend per share") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Database save will be added in the next step.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Holding")
        }
    }
}