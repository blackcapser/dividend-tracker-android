package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.domain.model.Holding
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.ErrorRed
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen
import java.text.DecimalFormat

@Composable
fun HoldingCard(
    holding: Holding,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val mutedTextColor = if (isDark) DarkTextMuted else LightTextMuted
    val dividerColor = if (isDark) DarkOutline else LightOutline
    val currencyFormatter = DecimalFormat("#,##0.##")
    val valueChange = holding.currentValue - holding.investedAmount
    val isPositiveChange = valueChange >= 0.0

    DividendCard(
        modifier = modifier,
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                InitialsAvatar(
                    initials = holding.ticker.substringBefore('.'),
                    size = 56.dp
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = holding.ticker,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = primaryTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = holding.companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = mutedTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "${holding.currency}${currencyFormatter.format(holding.currentValue)}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = primaryTextColor,
                        maxLines = 1
                    )

                    Text(
                        text = "${if (isPositiveChange) "↗" else "↘"} ${holding.currency}${currencyFormatter.format(kotlin.math.abs(valueChange))}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = if (isPositiveChange) SuccessGreen else ErrorRed,
                        maxLines = 1
                    )
                }
            }

            HorizontalDivider(color = dividerColor)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HoldingMetric(
                    label = "Shares",
                    value = currencyFormatter.format(holding.shares),
                    labelColor = secondaryTextColor,
                    valueColor = primaryTextColor,
                    modifier = Modifier.weight(1f)
                )

                HoldingMetric(
                    label = "Avg Price",
                    value = "${holding.currency}${currencyFormatter.format(holding.averagePrice)}",
                    labelColor = secondaryTextColor,
                    valueColor = primaryTextColor,
                    modifier = Modifier.weight(1f)
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Yield",
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryTextColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    YieldChip(text = "${DecimalFormat("0.##").format(holding.dividendYield)}%")
                }
            }
        }
    }
}

@Composable
private fun HoldingMetric(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = labelColor
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = valueColor,
            maxLines = 1
        )
    }
}

@Preview(name = "Holding Card")
@Composable
private fun HoldingCardPreview() {
    DividendTrackerTheme(darkTheme = true) {
        HoldingCard(
            holding = Holding(
                id = 1,
                companyName = "Alpha Services and Holdings",
                ticker = "ALPHA.AT",
                shares = 1500.0,
                averagePrice = 1.65,
                currentPrice = 1.72,
                annualDividendPerShare = 0.0298
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

