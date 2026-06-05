package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue

@Composable
fun InitialsAvatar(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val isDark = isSystemInDarkTheme()

    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = PrimaryBlue.copy(alpha = if (isDark) 0.26f else 0.14f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = initials.take(2).uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PrimaryBlue
            )
        }
    }
}

@Preview(name = "Initials Avatar")
@Composable
private fun InitialsAvatarPreview() {
    DividendTrackerTheme(darkTheme = true) {
        InitialsAvatar(initials = "AL")
    }
}

