package dev.elenivoreopoulou.dividendtracker.presentation.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendAppHeader
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenBottomPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenHorizontalPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTitleRow
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTopPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendTextField
import dev.elenivoreopoulou.dividendtracker.ui.components.HoldingCard
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen

@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    PortfolioScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange
    )
}

@Composable
private fun PortfolioScreen(
    uiState: PortfolioScreenUiState,
    onSearchQueryChange: (String) -> Unit
) {
    val isDark = isDividendInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val mutedTextColor = if (isDark) DarkTextMuted else LightTextMuted

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentPadding = PaddingValues(
            start = DividendScreenHorizontalPadding,
            top = DividendScreenTopPadding,
            end = DividendScreenHorizontalPadding,
            bottom = DividendScreenBottomPadding
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            DividendAppHeader()
        }

        item {
            DividendScreenTitleRow(
                title = "Holdings",
                trailingIcon = Icons.Outlined.Tune,
                trailingIconContentDescription = "Filter holdings"
            )
        }

        item {
            PortfolioSummaryCard(uiState = uiState.summary)
        }

        item {
            SearchHoldingsField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange
            )
        }

        if (uiState.filteredHoldings.isEmpty()) {
            item {
                EmptyHoldingsMessage(
                    query = uiState.searchQuery,
                    titleColor = primaryTextColor,
                    subtitleColor = mutedTextColor
                )
            }
        } else {
            items(
                items = uiState.filteredHoldings,
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
private fun PortfolioSummaryCard(uiState: PortfolioSummaryUiState) {
    val isDark = isDividendInDarkTheme()

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
    val isDark = isDividendInDarkTheme()
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
    val isDark = isDividendInDarkTheme()
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
        color = if (isDividendInDarkTheme()) DarkSurface else LightSurfaceSecondary
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

@Preview(
    name = "Portfolio Light",
    showBackground = true
)
@Composable
private fun PortfolioScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        PortfolioScreen(
            uiState = previewPortfolioUiState,
            onSearchQueryChange = {}
        )
    }
}

@Preview(
    name = "Portfolio Dark",
    showBackground = true
)
@Composable
private fun PortfolioScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        PortfolioScreen(
            uiState = previewPortfolioUiState,
            onSearchQueryChange = {}
        )
    }
}

private val previewPortfolioUiState = PortfolioScreenUiState.from(
    holdings = listOf(
        Holding(
            id = 1,
            companyName = "OPAP",
            ticker = "OPAP.AT",
            shares = 7250.0,
            averagePrice = 17.52,
            currentPrice = 17.60,
            annualDividendPerShare = 1.85
        ),
        Holding(
            id = 2,
            companyName = "Alpha Services and Holdings",
            ticker = "ALPHA.AT",
            shares = 1000.0,
            averagePrice = 1.65,
            currentPrice = 1.72,
            annualDividendPerShare = 0.0298
        ),
        Holding(
            id = 3,
            companyName = "Jumbo",
            ticker = "BELA.AT",
            shares = 100.0,
            averagePrice = 25.00,
            currentPrice = 27.40,
            annualDividendPerShare = 1.20
        )
    )
)

