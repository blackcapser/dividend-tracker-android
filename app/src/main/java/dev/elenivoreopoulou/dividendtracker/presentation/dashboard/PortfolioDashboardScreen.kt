package dev.elenivoreopoulou.dividendtracker.presentation.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendPayoutCard
import dev.elenivoreopoulou.dividendtracker.ui.components.GoalProgressCard
import dev.elenivoreopoulou.dividendtracker.ui.components.TrendChip
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import java.text.DecimalFormat

@Composable
fun PortfolioDashboardScreen() {
    val holdings = FakePortfolioData.holdings
    val portfolioValue = holdings.sumOf { it.currentValue }
    val annualDividends = holdings.sumOf { it.annualDividendIncome }
    val monthlyIncome = annualDividends / 12
    val monthlyGoal = 5000.0
    val progress = (monthlyIncome / monthlyGoal).toFloat().coerceIn(0f, 1f)
    val currencyFormatter = DecimalFormat("#,##0.0#")
    val isDark = isSystemInDarkTheme()

    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 42.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header(
            titleColor = primaryTextColor,
            subtitleColor = secondaryTextColor
        )

        PortfolioValueCard(
            title = "Total Portfolio Value",
            value = "€${currencyFormatter.format(portfolioValue)}",
            subtitle = "${holdings.size} holdings",
            trend = "+4.6%"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AnnualDividendCard(
                title = "Annual Dividends",
                value = "€${currencyFormatter.format(annualDividends)}",
                modifier = Modifier.weight(1f)
            )

            MonthlyAverageCard(
                title = "Monthly Average",
                value = "€${currencyFormatter.format(monthlyIncome)}",
                subtitle = "Yield: 10.69%",
                modifier = Modifier.weight(1f)
            )
        }

        GoalProgressCard(
            title = "Passive Income Goal",
            currentValue = "€${currencyFormatter.format(monthlyIncome)}",
            targetValue = "€${currencyFormatter.format(monthlyGoal)}",
            remainingText = "€${currencyFormatter.format(monthlyGoal - monthlyIncome)} remaining to reach your monthly goal.",
            progress = progress
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Upcoming Payouts",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = primaryTextColor
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "View all",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = PrimaryBlue
                )

                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        DividendPayoutCard(
            initials = "BE",
            title = "BELA.AT",
            subtitle = "Mar 15, 2026",
            amount = "+€120"
        )

        DividendPayoutCard(
            initials = "OP",
            title = "OPAP.AT",
            subtitle = "May 04, 2026",
            amount = "+€450"
        )

        AllocationCard()

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun Header(
    titleColor: Color,
    subtitleColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy((-2).dp)) {
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
            modifier = Modifier.size(42.dp),
            shape = CircleShape,
            color = if (isSystemInDarkTheme()) DarkSurfaceSecondary else LightSurfaceSecondary
        ) {
            Canvas(modifier = Modifier.padding(11.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                drawCircle(
                    color = subtitleColor,
                    radius = size.minDimension * 0.18f,
                    center = center,
                    style = Stroke(width = 2.2f)
                )

                repeat(8) { index ->
                    val angle = Math.toRadians((index * 45).toDouble())
                    val startRadius = size.minDimension * 0.34f
                    val endRadius = size.minDimension * 0.47f
                    drawLine(
                        color = subtitleColor,
                        start = Offset(
                            x = center.x + kotlin.math.cos(angle).toFloat() * startRadius,
                            y = center.y + kotlin.math.sin(angle).toFloat() * startRadius
                        ),
                        end = Offset(
                            x = center.x + kotlin.math.cos(angle).toFloat() * endRadius,
                            y = center.y + kotlin.math.sin(angle).toFloat() * endRadius
                        ),
                        strokeWidth = 2.2f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
private fun PortfolioValueCard(
    title: String,
    value: String,
    subtitle: String,
    trend: String
) {
    val isDark = isSystemInDarkTheme()
    val titleColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val valueColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 22.dp),
        shadowElevation = 12.dp,
        shadowColor = Color.Black.copy(alpha = if (isDark) 0.24f else 0.10f)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                color = titleColor
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = valueColor,
                maxLines = 1
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TrendChip(text = trend)

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    color = subtitleColor
                )
            }
        }
    }
}

@Composable
private fun AnnualDividendCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val titleColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val valueColor = if (isDark) DarkTextPrimary else LightTextPrimary

    DividendCard(
        modifier = modifier.height(128.dp),
        cornerRadius = 22.dp,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = valueColor,
                maxLines = 1
            )

            Sparkline(modifier = Modifier.fillMaxWidth().height(28.dp))
        }
    }
}

@Composable
private fun MonthlyAverageCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val titleColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        modifier = modifier.height(128.dp),
        cornerRadius = 22.dp,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = PrimaryBlue,
                maxLines = 1
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                color = subtitleColor
            )
        }
    }
}

@Composable
private fun Sparkline(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val points = listOf(0.70f, 0.62f, 0.63f, 0.58f, 0.49f, 0.55f, 0.40f, 0.45f, 0.31f)
        val step = size.width / (points.lastIndex.takeIf { it > 0 } ?: 1)
        val path = Path()

        points.forEachIndexed { index, point ->
            val x = index * step
            val y = size.height * point
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = PrimaryBlue,
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun AllocationCard() {
    val isDark = isSystemInDarkTheme()

    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 28.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PieChart,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Allocation",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = titleColor
                    )
                }

                Text(
                    text = "By Sector",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                    color = subtitleColor
                )
            }

            Canvas(
                modifier = Modifier
                    .size(84.dp)
            ) {
                val strokeWidth = 10.dp.toPx()
                val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
                val topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)

                drawArc(
                    color = PrimaryBlue,
                    startAngle = 128f,
                    sweepAngle = 230f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                drawArc(
                    color = if (isDark) DarkTextSecondary else LightTextSecondary.copy(alpha = 0.45f),
                    startAngle = 2f,
                    sweepAngle = 104f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                drawArc(
                    color = if (isDark) DarkSurfaceSecondary else LightSurfaceSecondary,
                    startAngle = 108f,
                    sweepAngle = 16f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
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