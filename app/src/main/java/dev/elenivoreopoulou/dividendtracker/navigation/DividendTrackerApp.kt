package dev.elenivoreopoulou.dividendtracker.navigation

import androidx.compose.foundation.background
import dev.elenivoreopoulou.dividendtracker.ui.theme.isDividendInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendBottomNavItem
import dev.elenivoreopoulou.dividendtracker.ui.components.DividendBottomNavigationBar
import dev.elenivoreopoulou.dividendtracker.ui.theme.DarkBackground
import dev.elenivoreopoulou.dividendtracker.ui.theme.LightBackground

@Composable
fun DividendTrackerApp() {
    val navController = rememberNavController()
    val isDark = isDividendInDarkTheme()

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

            DividendBottomNavigationBar(
                items = bottomItems.map { item -> item.navItem },
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

private sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    val navItem: DividendBottomNavItem = DividendBottomNavItem(
        route = route,
        label = label,
        icon = icon
    )

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
        icon = Icons.Outlined.AddCircleOutline
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