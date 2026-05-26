package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    valueColorOverride: Color? = null,
    @DrawableRes chartRes: Int? = null
) {
    val isDark = isSystemInDarkTheme()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = if (isDark) DarkSurface else LightSurface
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) DarkTextSecondary else LightTextSecondary
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = valueColorOverride
                    ?: if (isDark) DarkTextPrimary else LightTextPrimary
            )

            if (chartRes != null) {
                Image(
                    painter = painterResource(id = chartRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
            }

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDark) DarkTextMuted else LightTextMuted
                )
            }
        }
    }
}