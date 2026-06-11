package dev.elenivoreopoulou.dividendtracker.presentation.dividends

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
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
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendAppHeader
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenBottomPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenHorizontalPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTitleRow
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTopPadding
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurface
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

@Composable
fun DividendCalendarScreen(
    viewModel: DividendCalendarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DividendCalendarScreen(uiState = uiState)
}

@Composable
private fun DividendCalendarScreen(uiState: DividendCalendarUiState) {
    val isDark = isDividendInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = DividendScreenHorizontalPadding)
            .padding(top = DividendScreenTopPadding, bottom = DividendScreenBottomPadding),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        DividendAppHeader()

        DividendScreenTitleRow(title = "Calendar") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = uiState.selectedYear,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = primaryTextColor
                )

                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Select year",
                    tint = primaryTextColor,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        ExpectedIncomeCard(
            amount = uiState.expectedIncome
        )

        CalendarTimeline(
            months = uiState.months
        )
    }
}

@Composable
private fun ExpectedIncomeCard(amount: String) {
    val isDark = isDividendInDarkTheme()
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
    months: List<CalendarMonthUiState>
) {
    val isDark = isDividendInDarkTheme()
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
            color = if (isActive) PrimaryBlue else if (isDividendInDarkTheme()) DarkOutline else LightTextMuted.copy(alpha = 0.7f)
        ) {}
    }
}

@Composable
private fun MonthPayoutCard(
    month: CalendarMonthUiState,
    modifier: Modifier = Modifier
) {
    val isDark = isDividendInDarkTheme()
    val isActive = month.hasPayouts
    val containerColor = if (isDark) DarkSurface else LightSurface
    val inactiveContainerColor = if (isDark) DarkSurface.copy(alpha = 0.58f) else LightSurface.copy(alpha = 0.72f)
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextMuted else LightTextMuted
    val dividerColor = if (isDark) DarkOutline else LightOutline

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
                    text = month.totalPayoutAmount,
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
                            text = payout.formattedAmount,
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

private val CalendarMonthUiState.rowHeight: androidx.compose.ui.unit.Dp
    get() = when (payouts.size) {
        0 -> 58.dp
        1 -> 108.dp
        else -> 138.dp
    }

@Preview(
    name = "Calendar Light",
    showBackground = true
)
@Composable
private fun DividendCalendarScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        DividendCalendarScreen(uiState = previewCalendarUiState)
    }
}

@Preview(
    name = "Calendar Dark",
    showBackground = true
)
@Composable
private fun DividendCalendarScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendCalendarScreen(uiState = previewCalendarUiState)
    }
}

private val previewCalendarUiState = DividendCalendarUiState.from(
    months = listOf(
        CalendarMonthUiState(name = "Jan"),
        CalendarMonthUiState(name = "Feb"),
        CalendarMonthUiState(name = "Mar", payouts = listOf(CalendarPayoutUiState("BELA.AT", 120))),
        CalendarMonthUiState(name = "Apr"),
        CalendarMonthUiState(
            name = "May",
            payouts = listOf(
                CalendarPayoutUiState("OPAP.AT", 450),
                CalendarPayoutUiState("EEE.AT", 6256)
            )
        ),
        CalendarMonthUiState(name = "Jun", payouts = listOf(CalendarPayoutUiState("ALPHA.AT", 45))),
        CalendarMonthUiState(name = "Jul"),
        CalendarMonthUiState(name = "Aug"),
        CalendarMonthUiState(name = "Sep", payouts = listOf(CalendarPayoutUiState("OPAP.AT", 225))),
        CalendarMonthUiState(name = "Oct"),
        CalendarMonthUiState(name = "Nov"),
        CalendarMonthUiState(name = "Dec")
    )
)
