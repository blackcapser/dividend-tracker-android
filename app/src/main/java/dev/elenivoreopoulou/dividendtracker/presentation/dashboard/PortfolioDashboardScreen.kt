package dev.elenivoreopoulou.dividendtracker.presentation.dashboard

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
fun PortfolioDashboardScreen() {
    val holdings = FakePortfolioData.holdings

    val portfolioValue = holdings.sumOf { it.currentValue }
    val annualDividends = holdings.sumOf { it.annualDividendIncome }
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
            text = "Dividend Portfolio",
            style = MaterialTheme.typography.headlineMedium
        )

        SummaryCard(
            title = "Portfolio Value",
            value = "€${portfolioValue.toInt()}",
            subtitle = "${holdings.size} holdings"
        )

        SummaryCard(
            title = "Annual Dividends",
            value = "€${annualDividends.toInt()}",
            subtitle = "Expected yearly passive income"
        )

        SummaryCard(
            title = "Monthly Passive Income",
            value = "€${monthlyIncome.toInt()}",
            subtitle = "Goal: €${monthlyGoal.toInt()} / month"
        )

        Text(
            text = "Passive income progress",
            style = MaterialTheme.typography.titleMedium
        )

        LinearProgressIndicator(
            progress = { progress }
        )

        Text(
            text = "${(progress * 100).toInt()}% of monthly goal reached",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}