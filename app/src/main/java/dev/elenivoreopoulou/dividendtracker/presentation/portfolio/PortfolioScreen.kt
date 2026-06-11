package dev.elenivoreopoulou.dividendtracker.presentation.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendTextField
import dev.elenivoreopoulou.dividendtracker.ui.components.HoldingCard
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen
import java.text.DecimalFormat

@Composable
fun PortfolioScreen() {
    val holdings = FakePortfolioData.holdings.sortedBy { it.ticker }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val filteredHoldings = holdings.filter { holding ->
        holding.ticker.contains(searchQuery, ignoreCase = true) ||
            holding.companyName.contains(searchQuery, ignoreCase = true)
    }
    val uiState = PortfolioUiState.from(holdings)
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val mutedTextColor = if (isDark) DarkTextMuted else LightTextMuted

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 42.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            PortfolioHeader(
                titleColor = primaryTextColor,
                subtitleColor = secondaryTextColor
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Holdings",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = primaryTextColor
                )

                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter holdings",
                    tint = primaryTextColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        item {
            PortfolioSummaryCard(uiState = uiState)
        }

        item {
            SearchHoldingsField(
                value = searchQuery,
                onValueChange = { searchQuery = it }
            )
        }

        if (filteredHoldings.isEmpty()) {
            item {
                EmptyHoldingsMessage(
                    query = searchQuery,
                    titleColor = primaryTextColor,
                    subtitleColor = mutedTextColor
                )
            }
        } else {
            items(
                items = filteredHoldings,
                key = { holding -> holding.id }
            ) { holding ->
                HoldingCard(holding = holding)
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PortfolioHeader(
    titleColor: Color,
    subtitleColor: Color
) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy((-2).dp)
        ) {
            Text(
                text = "Dividend Portfolio",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = titleColor
            )

            Text(
                text = "Good morning, Investor",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                color = subtitleColor
            )
        }

        Surface(
            modifier = Modifier.size(54.dp),
            shape = CircleShape,
            color = if (isDark) DarkSurfaceSecondary else LightSurface
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isDark) Icons.Outlined.WbSunny else Icons.Outlined.DarkMode,
                    contentDescription = null,
                    tint = subtitleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
private fun PortfolioSummaryCard(uiState: PortfolioUiState) {
    val isDark = isSystemInDarkTheme()

    DividendCard(
        cornerRadius = 28.dp,
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 26.dp),
        shadowElevation = 14.dp,
        shadowColor = Color.Black.copy(alpha = if (isDark) 0.30f else 0.08f)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SummaryMetric(
                    label = "Invested",
                    value = uiState.investedAmount,
                    modifier = Modifier.weight(1f)
                )
                SummaryMetric(
                    label = "Current Value",
                    value = uiState.currentValue,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SummaryMetric(
                    label = "Annual Div",
                    value = uiState.annualDividendIncome,
                    valueColor = PrimaryBlue,
                    modifier = Modifier.weight(1f)
                )
                SummaryMetric(
                    label = "Yield on Cost",
                    value = uiState.yieldOnCost,
                    valueColor = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SummaryMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color? = null
) {
    val isDark = isSystemInDarkTheme()
    val labelColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val resolvedValueColor = valueColor ?: if (isDark) DarkTextPrimary else LightTextPrimary

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
            color = labelColor
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = resolvedValueColor,
            maxLines = 1
        )
    }
}

@Composable
private fun SearchHoldingsField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val borderColor = if (isDark) DarkOutline else LightOutline

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isDark) 0.dp else 2.dp,
                shape = RoundedCornerShape(22.dp),
                ambientColor = Color.Black.copy(alpha = 0.03f),
                spotColor = Color.Black.copy(alpha = 0.03f)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(22.dp)
            )
    ) {
        DividendTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = "Search holdings",
            leadingIcon = Icons.Outlined.Search
        )
    }
}

@Composable
private fun EmptyHoldingsMessage(
    query: String,
    titleColor: Color,
    subtitleColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = if (isSystemInDarkTheme()) DarkSurface else LightSurfaceSecondary
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No holdings found",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = titleColor
            )

            Text(
                text = "Try a different search term for \"$query\".",
                style = MaterialTheme.typography.bodyMedium,
                color = subtitleColor
            )
        }
    }
}

private data class PortfolioUiState(
    val investedAmount: String,
    val currentValue: String,
    val annualDividendIncome: String,
    val yieldOnCost: String
) {
    companion object {
        fun from(holdings: List<Holding>): PortfolioUiState {
            val investedAmount = holdings.sumOf { it.investedAmount }
            val currentValue = holdings.sumOf { it.currentValue }
            val annualDividendIncome = holdings.sumOf { it.annualDividendIncome }
            val yieldOnCost = if (investedAmount > 0) {
                annualDividendIncome / investedAmount * 100
            } else {
                0.0
            }
            val currencyFormatter = DecimalFormat("#,##0.0#")
            val percentFormatter = DecimalFormat("0.##")

            return PortfolioUiState(
                investedAmount = "€${currencyFormatter.format(investedAmount)}",
                currentValue = "€${currencyFormatter.format(currentValue)}",
                annualDividendIncome = "€${currencyFormatter.format(annualDividendIncome)}",
                yieldOnCost = "${percentFormatter.format(yieldOnCost)}%"
            )
        }
    }
}

@Preview(
    name = "Portfolio Light",
    showBackground = true
)
@Composable
private fun PortfolioScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        PortfolioScreen()
    }
}

@Preview(
    name = "Portfolio Dark",
    showBackground = true
)
@Composable
private fun PortfolioScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        PortfolioScreen()
    }
}

