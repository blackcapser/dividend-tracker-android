package dev.elenivoreopoulou.dividendtracker.presentation.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.ui.components.SummaryCard

@Composable
fun PassiveIncomeGoalScreen() {
    val annualDividends = FakePortfolioData.holdings.sumOf { it.annualDividendIncome }
    val monthlyIncome = annualDividends / 12
    val monthlyGoal = 5000.0
    val progress = (monthlyIncome / monthlyGoal).toFloat().coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Passive Income Goal",
            style = MaterialTheme.typography.headlineMedium
        )

        SummaryCard(
            title = "Current Monthly Income",
            value = "€${monthlyIncome.toInt()}"
        )

        SummaryCard(
            title = "Target Monthly Income",
            value = "€${monthlyGoal.toInt()}"
        )

        Text(
            text = "Progress",
            style = MaterialTheme.typography.titleMedium
        )

        LinearProgressIndicator(
            progress = { progress }
        )

        Text(
            text = "${(progress * 100).toInt()}% completed",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Remaining monthly income needed: €${(monthlyGoal - monthlyIncome).toInt()}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}