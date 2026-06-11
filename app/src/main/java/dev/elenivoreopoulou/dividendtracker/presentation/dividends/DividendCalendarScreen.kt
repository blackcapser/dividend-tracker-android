package dev.elenivoreopoulou.dividendtracker.presentation.dividends

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
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
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen
import java.text.DecimalFormat

@Composable
fun DividendCalendarScreen() {
    val months = calendarMonths
    val annualExpectedIncome = months.sumOf { month -> month.payouts.sumOf { it.amount } }
    val currencyFormatter = DecimalFormat("#,##0")
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
            .padding(top = 56.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        CalendarHeader(
            titleColor = primaryTextColor,
            subtitleColor = secondaryTextColor
        )

        CalendarTitleRow(
            titleColor = primaryTextColor
        )

        ExpectedIncomeCard(
            amount = "€${currencyFormatter.format(annualExpectedIncome)}"
        )

        CalendarTimeline(
            months = months,
            currencyFormatter = currencyFormatter
        )
    }
}

@Composable
private fun CalendarHeader(
    titleColor: Color,
    subtitleColor: Color
) {
    val isDark = isSystemInDarkTheme()

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
private fun CalendarTitleRow(titleColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calendar",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = titleColor
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "2026",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = titleColor
            )

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Select year",
                tint = titleColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun ExpectedIncomeCard(amount: String) {
    val isDark = isSystemInDarkTheme()
    val titleColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val valueColor = if (isDark) DarkTextPrimary else LightTextPrimary

    DividendCard(
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = PrimaryBlue.copy(alpha = if (isDark) 0.26f else 0.14f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Expected this year",
                    style = MaterialTheme.typography.bodySmall,
                    color = titleColor
                )

                Text(
                    text = amount,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = valueColor
                )
            }
        }
    }
}

@Composable
private fun CalendarTimeline(
    months: List<CalendarMonth>,
    currencyFormatter: DecimalFormat
) {
    val isDark = isSystemInDarkTheme()
    val lineColor = if (isDark) DarkOutline else LightTextMuted.copy(alpha = 0.68f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(IntrinsicSize.Min)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val x = size.width / 2f
                drawLine(
                    color = lineColor,
                    start = androidx.compose.ui.geometry.Offset(x, 8.dp.toPx()),
                    end = androidx.compose.ui.geometry.Offset(x, size.height - 8.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                months.forEach { month ->
                    TimelineMarker(
                        isActive = month.hasPayouts,
                        modifier = Modifier.height(month.rowHeight)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            months.forEach { month ->
                MonthPayoutCard(
                    month = month,
                    currencyFormatter = currencyFormatter,
                    modifier = Modifier.height(month.rowHeight)
                )
            }
        }
    }
}

@Composable
private fun TimelineMarker(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(if (isActive) 10.dp else 9.dp),
            shape = CircleShape,
            color = if (isActive) PrimaryBlue else if (isSystemInDarkTheme()) DarkOutline else LightTextMuted.copy(alpha = 0.7f)
        ) {}
    }
}

@Composable
private fun MonthPayoutCard(
    month: CalendarMonth,
    currencyFormatter: DecimalFormat,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val isActive = month.hasPayouts
    val containerColor = if (isDark) DarkSurface else LightSurface
    val inactiveContainerColor = if (isDark) DarkSurface.copy(alpha = 0.58f) else LightSurface.copy(alpha = 0.72f)
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextMuted else LightTextMuted
    val dividerColor = if (isDark) DarkOutline else LightOutline
    val amount = month.payouts.sumOf { it.amount }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = if (isActive) containerColor else inactiveContainerColor,
        border = if (isActive && isDark) BorderStroke(1.dp, DarkTextPrimary.copy(alpha = 0.82f)) else null
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = if (isActive) 18.dp else 0.dp),
            verticalArrangement = if (isActive) Arrangement.spacedBy(12.dp) else Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = month.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isActive) primaryTextColor else secondaryTextColor
                )

                Text(
                    text = "€${currencyFormatter.format(amount)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isActive) SuccessGreen else secondaryTextColor,
                    textAlign = TextAlign.End
                )
            }

            if (isActive) {
                HorizontalDivider(color = dividerColor)

                month.payouts.forEach { payout ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = payout.ticker,
                            style = MaterialTheme.typography.bodyMedium,
                            color = secondaryTextColor
                        )

                        Text(
                            text = "€${currencyFormatter.format(payout.amount)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = primaryTextColor,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

private data class CalendarMonth(
    val name: String,
    val payouts: List<CalendarPayout> = emptyList()
) {
    val hasPayouts: Boolean = payouts.isNotEmpty()
    val rowHeight: androidx.compose.ui.unit.Dp
        get() = when (payouts.size) {
            0 -> 58.dp
            1 -> 108.dp
            else -> 138.dp
        }
}

private data class CalendarPayout(
    val ticker: String,
    val amount: Int
)

private val calendarMonths = listOf(
    CalendarMonth(name = "Jan"),
    CalendarMonth(name = "Feb"),
    CalendarMonth(name = "Mar", payouts = listOf(CalendarPayout("BELA.AT", 120))),
    CalendarMonth(name = "Apr"),
    CalendarMonth(
        name = "May",
        payouts = listOf(
            CalendarPayout("OPAP.AT", 450),
            CalendarPayout("EEE.AT", 6256)
        )
    ),
    CalendarMonth(name = "Jun", payouts = listOf(CalendarPayout("ALPHA.AT", 45))),
    CalendarMonth(name = "Jul"),
    CalendarMonth(name = "Aug"),
    CalendarMonth(name = "Sep", payouts = listOf(CalendarPayout("OPAP.AT", 225))),
    CalendarMonth(name = "Oct"),
    CalendarMonth(name = "Nov"),
    CalendarMonth(name = "Dec")
)

@Preview(
    name = "Calendar Light",
    showBackground = true
)
@Composable
private fun DividendCalendarScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        DividendCalendarScreen()
    }
}

@Preview(
    name = "Calendar Dark",
    showBackground = true
)
@Composable
private fun DividendCalendarScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendCalendarScreen()
    }
}