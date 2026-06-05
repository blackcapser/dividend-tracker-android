package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue
import java.text.DecimalFormat

@Composable
fun GoalProgressCard(
    title: String,
    currentValue: String,
    targetValue: String,
    remainingText: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val clampedProgress = progress.coerceIn(0f, 1f)
    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val secondaryColor = if (isDark) DarkTextSecondary else LightTextSecondary
    val mutedColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        modifier = modifier,
        cornerRadius = 22.dp
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = titleColor
                    )

                    Text(
                        text = "$currentValue / $targetValue",
                        style = MaterialTheme.typography.bodySmall,
                        color = secondaryColor
                    )
                }

                Text(
                    text = "${DecimalFormat("0.0").format(clampedProgress * 100)}%",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = PrimaryBlue
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(PrimaryBlue.copy(alpha = if (isDark) 0.32f else 0.18f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(clampedProgress)
                        .height(16.dp)
                        .clip(RoundedCornerShape(50))
                        .background(PrimaryBlue)
                )
            }

            Text(
                text = remainingText,
                style = MaterialTheme.typography.bodySmall,
                color = mutedColor
            )
        }
    }
}

@Preview(name = "Goal Progress Card")
@Composable
private fun GoalProgressCardPreview() {
    DividendTrackerTheme(darkTheme = true) {
        GoalProgressCard(
            title = "Passive Income Goal",
            currentValue = "€1,131.43",
            targetValue = "€5,000",
            remainingText = "€3,868.57 remaining to reach your monthly goal.",
            progress = 0.226f
        )
    }
}

