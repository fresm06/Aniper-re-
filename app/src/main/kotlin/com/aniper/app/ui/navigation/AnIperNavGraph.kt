package com.aniper.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aniper.app.ui.screen.create.CreateCharacterScreen
import com.aniper.app.ui.screen.home.HomeScreen
import com.aniper.app.ui.screen.market.MarketScreen
import com.aniper.app.ui.screen.settings.SettingsScreen
import com.aniper.app.ui.theme.AccentPurple
import com.aniper.app.ui.theme.BackgroundSecondary
import com.aniper.app.ui.theme.TextPrimary
import com.aniper.app.ui.theme.TextSecondary

sealed class Screen(val route: String, val label: String) {
    object Home : Screen("home", "홈")
    object Market : Screen("market", "마켓")
    object Create : Screen("create", "생성")
    object Settings : Screen("settings", "설정")
}

@Composable
fun AnIperNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundSecondary
            ) {
                listOf(
                    Screen.Home to Icons.Default.Home,
                    Screen.Market to Icons.Default.ShoppingCart,
                    Screen.Create to Icons.Default.Add,
                    Screen.Settings to Icons.Default.Settings
                ).forEach { (screen, icon) ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = screen.label,
                                tint = if (currentRoute == screen.route) AccentPurple else TextSecondary
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                color = if (currentRoute == screen.route) AccentPurple else TextSecondary,
                                style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                            )
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Market.route) {
                MarketScreen(navController)
            }
            composable(Screen.Create.route) {
                CreateCharacterScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}
