package dev.elenivoreopoulou.dividendtracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun DividendTrackerApp() {
    val navController = rememberNavController()

    val bottomItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Portfolio,
        BottomNavItem.Dividends,
        BottomNavItem.Goals,
        BottomNavItem.AddHolding
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination

                bottomItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
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
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(text = item.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
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
        icon = Icons.Default.BarChart
    )

    data object Portfolio : BottomNavItem(
        route = "portfolio",
        label = "Portfolio",
        icon = Icons.Default.BarChart
    )

    data object Dividends : BottomNavItem(
        route = "dividends",
        label = "Dividends",
        icon = Icons.Default.CalendarMonth
    )

    data object Goals : BottomNavItem(
        route = "goals",
        label = "Goals",
        icon = Icons.Default.Flag
    )

    data object AddHolding : BottomNavItem(
        route = "add_holding",
        label = "Add",
        icon = Icons.Default.Add
    )
}