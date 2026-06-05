package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.SuccessGreen

@Composable
fun DividendPayoutCard(
    initials: String,
    title: String,
    subtitle: String,
    amount: String,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextMuted else LightTextMuted

    DividendCard(
        modifier = modifier,
        cornerRadius = 22.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InitialsAvatar(initials = initials, size = 40.dp)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = titleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = subtitleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = SuccessGreen,
                maxLines = 1
            )
        }
    }
}

@Preview(name = "Dividend Payout Card")
@Composable
private fun DividendPayoutCardPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendPayoutCard(
            initials = "BE",
            title = "BELA.AT",
            subtitle = "Mar 15, 2026",
            amount = "+€120",
            modifier = Modifier.padding(16.dp)
        )
    }
}

