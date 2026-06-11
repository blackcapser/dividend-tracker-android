package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
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

@Composable
fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trend: String? = null
) {

    val isDarkTheme = isSystemInDarkTheme()

    val cardColor =
        if (isDarkTheme) DarkSurface else LightSurface

    val titleColor =
        if (isDarkTheme) DarkTextSecondary else LightTextSecondary

    val valueColor =
        if (isDarkTheme) DarkTextPrimary else LightTextPrimary

    val subtitleColor =
        if (isDarkTheme) DarkTextMuted else LightTextMuted

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = PrimaryBlue.copy(alpha = 0.08f),
                spotColor = PrimaryBlue.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(28.dp),
        color = cardColor
    ) {

        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = titleColor
            )

            Text(
                text = value,
                style = MaterialTheme.typography.displayMedium,
                color = valueColor
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (trend != null) {

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = SuccessGreen.copy(alpha = 0.16f)
                    ) {

                        Text(
                            text = trend,
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 8.dp
                            ),
                            style = MaterialTheme.typography.labelLarge,
                            color = SuccessGreen
                        )
                    }
                }

                if (subtitle != null) {

                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = subtitleColor
                    )
                }
            }
        }
    }
}