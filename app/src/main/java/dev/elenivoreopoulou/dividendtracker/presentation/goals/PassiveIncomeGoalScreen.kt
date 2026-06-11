package dev.elenivoreopoulou.dividendtracker.presentation.goals

import androidx.compose.foundation.background
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.data.model.FakePortfolioData
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendAppHeader
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendCard
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenBottomPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenHorizontalPadding
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTitleRow
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendScreenTopPadding
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightOutline
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen
import java.text.DecimalFormat

@Composable
fun PassiveIncomeGoalScreen() {
    val annualDividends = FakePortfolioData.holdings.sumOf { it.annualDividendIncome }
    val monthlyIncome = annualDividends / 12
    val monthlyGoal = 5000.0
    val progress = (monthlyIncome / monthlyGoal).toFloat().coerceIn(0f, 1f)
    val remainingMonthlyIncome = (monthlyGoal - monthlyIncome).coerceAtLeast(0.0)
    val currencyFormatter = DecimalFormat("#,##0.##")
    val percentFormatter = DecimalFormat("0.#")
    val isDark = isDividendInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val mutedTextColor = if (isDark) DarkTextMuted else LightTextMuted

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

        DividendScreenTitleRow(
            title = "Goals",
            trailingIcon = Icons.Outlined.TrackChanges,
            trailingIconContentDescription = "Goals"
        )

        MonthlyGoalCard(
            targetMonthlyIncome = "€${currencyFormatter.format(monthlyGoal)}",
            currentMonthlyIncome = "€${currencyFormatter.format(monthlyIncome)}",
            progressPercent = "${percentFormatter.format(progress * 100)}%",
            remainingMonthlyIncome = "€${currencyFormatter.format(remainingMonthlyIncome)}",
            progress = progress
        )

        Text(
            text = "Projections",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            color = primaryTextColor,
            modifier = Modifier.padding(top = 2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            ProjectionCard(
                icon = Icons.Outlined.Schedule,
                label = "Est. Time to Goal",
                value = "4.2 Years",
                iconColor = PrimaryBlue,
                modifier = Modifier.weight(1f)
            )

            ProjectionCard(
                icon = Icons.AutoMirrored.Outlined.TrendingUp,
                label = "Required Growth",
                value = "12% / yr",
                iconColor = SuccessGreen,
                modifier = Modifier.weight(1f)
            )
        }

        AccelerateProgressCard(
            titleColor = primaryTextColor,
            mutedTextColor = mutedTextColor
        )
    }
}


@Composable
private fun MonthlyGoalCard(
    targetMonthlyIncome: String,
    currentMonthlyIncome: String,
    progressPercent: String,
    remainingMonthlyIncome: String,
    progress: Float
) {
    val isDark = isDividendInDarkTheme()
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val mutedTextColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        cornerRadius = 28.dp,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 34.dp),
        shadowElevation = if (isDark) 12.dp else 0.dp,
        shadowColor = Color.Black.copy(alpha = 0.22f)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Target Monthly Income",
                    style = MaterialTheme.typography.bodyLarge,
                    color = secondaryTextColor
                )

                Text(
                    text = targetMonthlyIncome,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = primaryTextColor
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Current: $currentMonthlyIncome",
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryTextColor
                    )

                    Text(
                        text = progressPercent,
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryTextColor
                    )
                }

                GoalProgressBar(progress = progress)
            }

            Text(
                text = buildAnnotatedString {
                    append("You need ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = primaryTextColor)) {
                        append(remainingMonthlyIncome)
                    }
                    append(" more per month\nto reach your goal.")
                },
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 25.sp),
                color = mutedTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GoalProgressBar(progress: Float) {
    val isDark = isDividendInDarkTheme()
    val trackColor = PrimaryBlue.copy(alpha = if (isDark) 0.36f else 0.20f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .clip(RoundedCornerShape(50))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(16.dp)
                .clip(RoundedCornerShape(50))
                .background(PrimaryBlue)
        )
    }
}

@Composable
private fun ProjectionCard(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    val isDark = isDividendInDarkTheme()
    val primaryTextColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryTextColor = if (isDark) DarkTextSecondary else LightTextSecondary

    DividendCard(
        modifier = modifier.height(144.dp),
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconBadge(
                icon = icon,
                iconColor = iconColor,
                size = 34.dp
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = secondaryTextColor,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = primaryTextColor,
                modifier = Modifier.padding(top = 14.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AccelerateProgressCard(
    titleColor: Color,
    mutedTextColor: Color
) {
    DividendCard(
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 20.dp),
        shadowElevation = if (isDividendInDarkTheme()) 10.dp else 0.dp,
        shadowColor = Color.Black.copy(alpha = 0.22f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconBadge(
                icon = Icons.Outlined.Bolt,
                iconColor = PrimaryBlue,
                size = 48.dp,
                iconSize = 28.dp
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Accelerate Progress",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = titleColor
                )

                Text(
                    text = "Invest €500/month more\nto achieve your goal 1.5 years sooner",
                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 24.sp),
                    color = mutedTextColor
                )

                AdjustContributionsButton()
            }
        }
    }
}

@Composable
private fun IconBadge(
    icon: ImageVector,
    iconColor: Color,
    size: Dp,
    iconSize: Dp = 22.dp
) {
    Surface(
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = iconColor.copy(alpha = if (isDividendInDarkTheme()) 0.16f else 0.18f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun AdjustContributionsButton() {
    val isDark = isDividendInDarkTheme()
    val contentColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val borderColor = if (isDark) DarkOutline else LightOutline

    OutlinedButton(
        onClick = {},
        shape = RoundedCornerShape(50),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = contentColor),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Adjust Contributions",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(
    name = "Goals Light",
    showBackground = true
)
@Composable
private fun PassiveIncomeGoalScreenLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        PassiveIncomeGoalScreen()
    }
}

@Preview(
    name = "Goals Dark",
    showBackground = true
)
@Composable
private fun PassiveIncomeGoalScreenDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        PassiveIncomeGoalScreen()
    }
}