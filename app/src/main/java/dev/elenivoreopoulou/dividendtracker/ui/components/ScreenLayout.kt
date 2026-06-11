package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkSurfaceSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightSurface
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextPrimary
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextSecondary
import dev.elenivoreopoulou.dividendtracker.ui.theme.currentDividendThemeToggle
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme

val DividendScreenHorizontalPadding = 24.dp
val DividendScreenTopPadding = 56.dp
val DividendScreenBottomPadding = 32.dp

@Composable
fun DividendAppHeader(
    modifier: Modifier = Modifier,
    title: String = "Dividend Portfolio",
    subtitle: String = "Good morning, Investor"
) {
    val isDark = isDividendInDarkTheme()
    val toggleTheme = currentDividendThemeToggle()
    val titleColor = if (isDark) DarkTextPrimary else LightTextPrimary
    val subtitleColor = if (isDark) DarkTextSecondary else LightTextSecondary

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy((-2).dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = titleColor
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                color = subtitleColor
            )
        }

        Surface(
            modifier = Modifier
                .size(54.dp)
                .clickable(onClick = toggleTheme),
            shape = CircleShape,
            color = if (isDark) DarkSurfaceSecondary else LightSurface
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isDark) Icons.Outlined.WbSunny else Icons.Outlined.DarkMode,
                    contentDescription = if (isDark) "Switch to light mode" else "Switch to dark mode",
                    tint = subtitleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun DividendScreenTitleRow(
    title: String,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    trailingIconContentDescription: String? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    val titleColor = if (isDividendInDarkTheme()) DarkTextPrimary else LightTextPrimary

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = titleColor
        )

        when {
            trailingContent != null -> trailingContent()
            trailingIcon != null -> Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconContentDescription,
                tint = titleColor,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
fun DividendBackTitleRow(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val titleColor = if (isDividendInDarkTheme()) DarkTextPrimary else LightTextPrimary

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Go back",
            tint = titleColor,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onBackClick)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = titleColor
        )
    }
}

@Preview(name = "App Header")
@Composable
private fun DividendAppHeaderPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendAppHeader()
    }
}

@Preview(name = "Screen Title Row")
@Composable
private fun DividendScreenTitleRowPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendScreenTitleRow(
            title = "Goals",
            trailingIcon = Icons.Outlined.TrackChanges,
            trailingIconContentDescription = "Goals"
        )
    }
}

@Preview(name = "Back Title Row")
@Composable
private fun DividendBackTitleRowPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendBackTitleRow(
            title = "Add Holding",
            onBackClick = {}
        )
    }
}


