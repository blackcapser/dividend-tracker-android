package dev.elenivoreopoulou.dividendtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.DividendTrackerTheme
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue

data class DividendBottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun DividendBottomNavigationBar(
    items: List<DividendBottomNavItem>,
    currentRoute: String?,
    onItemClick: (DividendBottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isDividendInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val mutedColor = if (isDark) DarkTextMuted else LightTextMuted

    Surface(
        modifier = modifier,
        color = backgroundColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            HorizontalDivider(
                color = mutedColor.copy(alpha = 0.45f),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    BottomNavButton(
                        item = item,
                        selected = currentRoute == item.route,
                        mutedColor = mutedColor,
                        onClick = { onItemClick(item) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

private val previewBottomNavItems = listOf(
    DividendBottomNavItem("dashboard", "Home", Icons.Outlined.Home),
    DividendBottomNavItem("portfolio", "Portfolio", Icons.Outlined.PieChart),
    DividendBottomNavItem("add_holding", "Add", Icons.Outlined.AddCircleOutline),
    DividendBottomNavItem("goals", "Goals", Icons.Outlined.TrackChanges),
    DividendBottomNavItem("dividends", "Calendar", Icons.Outlined.CalendarMonth)
)

@Preview(name = "Bottom Navigation Light", showBackground = true)
@Composable
private fun DividendBottomNavigationLightPreview() {
    DividendTrackerTheme(darkTheme = false) {
        DividendBottomNavigationBar(
            items = previewBottomNavItems,
            currentRoute = "goals",
            onItemClick = {}
        )
    }
}

@Preview(name = "Bottom Navigation Dark", showBackground = true)
@Composable
private fun DividendBottomNavigationDarkPreview() {
    DividendTrackerTheme(darkTheme = true) {
        DividendBottomNavigationBar(
            items = previewBottomNavItems,
            currentRoute = "dividends",
            onItemClick = {}
        )
    }
}

@Composable
private fun BottomNavButton(
    item: DividendBottomNavItem,
    selected: Boolean,
    mutedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val itemColor = if (selected) PrimaryBlue else mutedColor

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = itemColor,
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.label,
            color = itemColor,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

