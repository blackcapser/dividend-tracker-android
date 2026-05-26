package dev.elenivoreopoulou.dividendtracker.presentation.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.ui.components.StatCard
import dev.elenivoreopoulou.dividendtracker.ui.components.SummaryCard
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen
import androidx.compose.ui.tooling.preview.Preview
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.R

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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dividend Portfolio",
            style = MaterialTheme.typography.titleLarge
        )

        SummaryCard(
            title = "Total Portfolio Value",
            value = "€${portfolioValue.toInt()}",
            subtitle = "${holdings.size} holdings",
            trend = "+4.6%"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard(
                title = "Annual Dividends",
                value = "€${annualDividends.toInt()}",
                modifier = Modifier.weight(1f),
                chartRes = R.drawable.ic_sparkline
            )

            StatCard(
                title = "Monthly Average",
                value = "€${monthlyIncome.toInt()}",
                subtitle = "Yield: 10.69%",
                modifier = Modifier.weight(1f),
                valueColorOverride = PrimaryBlue
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = if (isSystemInDarkTheme()) DarkSurface else LightSurface
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Passive Income Goal",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "€${monthlyIncome.toInt()} / €${monthlyGoal.toInt()}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSystemInDarkTheme()) {
                                DarkTextSecondary
                            } else {
                                LightTextSecondary
                            }
                        )
                    }

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = PrimaryBlue
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(50))
                        .background(PrimaryBlue.copy(alpha = 0.18f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(14.dp)
                            .clip(RoundedCornerShape(50))
                            .background(PrimaryBlue)
                    )
                }

                Text(
                    text = "€${(monthlyGoal - monthlyIncome).toInt()} remaining to reach your monthly goal.",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSystemInDarkTheme()) {
                        DarkTextMuted
                    } else {
                        LightTextMuted
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Upcoming Payouts",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(onClick = {}) {
                Text(text = "View all")
            }
        }

        PayoutCard(
            ticker = "BELA.AT",
            date = "Mar 15, 2026",
            amount = "+€120"
        )

        PayoutCard(
            ticker = "OPAP.AT",
            date = "May 04, 2026",
            amount = "+€450"
        )

        AllocationCard()

        Spacer(modifier = Modifier.height(96.dp))
    }
}

@Composable
private fun PayoutCard(
    ticker: String,
    date: String,
    amount: String
) {
    val isDark = isSystemInDarkTheme()

    val cardColor = if (isDark) DarkSurface else LightSurface
    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = cardColor
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.padding(end = 14.dp),
                shape = CircleShape,
                color = PrimaryBlue.copy(alpha = 0.14f)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = ticker,
                    style = MaterialTheme.typography.labelLarge,
                    color = titleColor
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = subtitleColor
                )
            }

            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium,
                color = SuccessGreen
            )
        }
    }
}

@Composable
private fun AllocationCard() {
    val isDark = isSystemInDarkTheme()

    val cardColor = if (isDark) DarkSurface else LightSurface
    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = cardColor
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Allocation",
                    style = MaterialTheme.typography.titleMedium,
                    color = titleColor
                )

                Text(
                    text = "By Sector",
                    style = MaterialTheme.typography.bodySmall,
                    color = subtitleColor
                )
            }

            Canvas(
                modifier = Modifier
                    .height(76.dp)
                    .fillMaxWidth(0.36f)
            ) {
                drawArc(
                    color = PrimaryBlue,
                    startAngle = -90f,
                    sweepAngle = 230f,
                    useCenter = false,
                    size = Size(size.height, size.height),
                    style = Stroke(width = 14f, cap = StrokeCap.Round)
                )

                drawArc(
                    color = if (isDark) DarkTextSecondary else LightTextSecondary.copy(alpha = 0.45f),
                    startAngle = 150f,
                    sweepAngle = 110f,
                    useCenter = false,
                    size = Size(size.height, size.height),
                    style = Stroke(width = 14f, cap = StrokeCap.Round)
                )
            }
        }
    }
}

@Preview(
    name = "Dashboard Light",
    showBackground = true
)
@Composable
private fun PortfolioDashboardScreenLightPreview() {
    DividendTrackerTheme(
        darkTheme = false
    ) {
        PortfolioDashboardScreen()
    }
}

@Preview(
    name = "Dashboard Dark",
    showBackground = true
)
@Composable
private fun PortfolioDashboardScreenDarkPreview() {
    DividendTrackerTheme(
        darkTheme = true
    ) {
        PortfolioDashboardScreen()
    }
}