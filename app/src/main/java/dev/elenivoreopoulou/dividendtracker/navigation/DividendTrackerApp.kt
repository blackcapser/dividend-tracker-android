package dev.elenivoreopoulou.dividendtracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.elenivoreopoulou.dividendtracker.presentation.addholding.AddHoldingScreen
import dev.elenivoreopoulou.dividendtracker.presentation.dashboard.PortfolioDashboardScreen
import dev.elenivoreopoulou.dividendtracker.presentation.dividends.DividendCalendarScreen
import dev.elenivoreopoulou.dividendtracker.presentation.goals.PassiveIncomeGoalScreen
import dev.elenivoreopoulou.dividendtracker.presentation.portfolio.PortfolioScreen
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightTextMuted
import dev.elenivoreopoulou.dividendtracker.ui.theme.PrimaryBlue

@Composable
fun DividendTrackerApp() {
    val navController = rememberNavController()
    val isDark = isSystemInDarkTheme()

    val bottomItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Portfolio,
        BottomNavItem.AddHolding,
        BottomNavItem.Goals,
        BottomNavItem.Dividends
    )

    Scaffold(
        bottomBar = {
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            val currentRoute = bottomItems.firstOrNull { item ->
                currentDestination?.hierarchy?.any { it.route == item.route } == true
            }?.route

            DividendBottomBar(
                items = bottomItems,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier
                .background(if (isDark) DarkBackground else LightBackground)
                .padding(paddingValues)
        ) {
            composable(BottomNavItem.Dashboard.route) {
                PortfolioDashboardScreen()
            }

            composable(BottomNavItem.Portfolio.route) {
                PortfolioScreen()
            }

            composable(BottomNavItem.Dividends.route) {
                DividendCalendarScreen()
            }

            composable(BottomNavItem.Goals.route) {
                PassiveIncomeGoalScreen()
            }

            composable(BottomNavItem.AddHolding.route) {
                AddHoldingScreen()
            }
        }
    }
}

@Composable
private fun DividendBottomBar(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) DarkBackground else LightBackground
    val mutedColor = if (isDark) DarkTextMuted else LightTextMuted

    Surface(
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
                modifier = Modifier.padding(horizontal = 24.dp),
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
                    val selected = currentRoute == item.route

                    if (item == BottomNavItem.AddHolding) {
                        CenterAddNavItem(
                            item = item,
                            mutedColor = mutedColor,
                            onClick = { onItemClick(item) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        BottomNavButton(
                            item = item,
                            selected = selected,
                            mutedColor = mutedColor,
                            onClick = { onItemClick(item) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavButton(
    item: BottomNavItem,
    selected: Boolean,
    mutedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val iconColor = if (selected) Color.White else mutedColor
    val labelColor = if (selected) PrimaryBlue else mutedColor

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
        Box(
            modifier = Modifier
                .width(56.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (selected) PrimaryBlue.copy(alpha = 0.92f) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(27.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.label,
            color = labelColor,
            style = androidx.compose.material3.MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun CenterAddNavItem(
    item: BottomNavItem,
    mutedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

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
        Box(
            modifier = Modifier
                .size(58.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.2f),
                    spotColor = PrimaryBlue.copy(alpha = 0.35f)
                )
                .clip(CircleShape)
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = item.label,
            color = mutedColor,
            style = androidx.compose.material3.MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

private sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : BottomNavItem(
        route = "dashboard",
        label = "Home",
        icon = Icons.Outlined.Home
    )

    data object Portfolio : BottomNavItem(
        route = "portfolio",
        label = "Portfolio",
        icon = Icons.Outlined.PieChart
    )

    data object AddHolding : BottomNavItem(
        route = "add_holding",
        label = "Add",
        icon = Icons.Outlined.Add
    )

    data object Dividends : BottomNavItem(
        route = "dividends",
        label = "Calendar",
        icon = Icons.Outlined.CalendarMonth
    )

    data object Goals : BottomNavItem(
        route = "goals",
        label = "Goals",
        icon = Icons.Outlined.TrackChanges
    )
}