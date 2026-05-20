package dev.elenivoreopoulou.dividendtracker.presentation.portfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding

@Composable
fun PortfolioScreen() {
    val holdings = FakePortfolioData.holdings

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Holdings",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(holdings) { holding ->
                HoldingCard(holding = holding)
            }
        }
    }
}

@Composable
private fun HoldingCard(
    holding: Holding
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "${holding.companyName} (${holding.ticker})",
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "Shares: ${holding.shares.toInt()}")
            Text(text = "Average price: €${holding.averagePrice}")
            Text(text = "Current value: €${holding.currentValue.toInt()}")
            Text(text = "Annual dividend income: €${holding.annualDividendIncome.toInt()}")
            Text(text = "Dividend yield: ${"%.2f".format(holding.dividendYield)}%")
        }
    }
}