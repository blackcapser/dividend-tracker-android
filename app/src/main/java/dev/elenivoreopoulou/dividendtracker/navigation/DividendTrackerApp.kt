package dev.elenivoreopoulou.dividendtracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider

@Composable
fun DividendTrackerApp() {
    val navController = rememberNavController()
    val isDark = isSystemInDarkTheme()

    val bottomItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Portfolio,
        BottomNavItem.AddHolding,
        BottomNavItem.Dividends,
        BottomNavItem.Goals
    )

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier.background(if (isDark) DarkBackground else LightBackground)
            ) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    color = if (isDark) DarkTextMuted else LightTextMuted,
                    thickness = 1.dp
                )
            NavigationBar(
                containerColor = if (isDark) DarkBackground else LightBackground,
                tonalElevation = 0.dp
            ) {
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination

                bottomItems.forEach { item ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == item.route } == true

                    if (item == BottomNavItem.AddHolding) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                modifier = Modifier.size(64.dp),
                                shape = CircleShape,
                                containerColor = PrimaryBlue,
                                contentColor = Color.White
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = item.label,
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                        }
                    } else {
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    modifier = Modifier.size(28.dp)
                                )
                            },
                            label = {
                                Text(text = item.label)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryBlue,
                                selectedTextColor = PrimaryBlue,
                                unselectedIconColor = if (isDark) DarkTextMuted else LightTextMuted,
                                unselectedTextColor = if (isDark) DarkTextMuted else LightTextMuted,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
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

private sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
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
        label = "Dividends",
        icon = Icons.Outlined.CalendarMonth
    )

    data object Goals : BottomNavItem(
        route = "goals",
        label = "Goals",
        icon = Icons.Outlined.Flag
    )
}