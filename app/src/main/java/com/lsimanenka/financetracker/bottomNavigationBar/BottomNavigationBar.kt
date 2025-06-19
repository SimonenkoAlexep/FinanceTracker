package com.lsimanenka.financetracker.bottomNavigationBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lsimanenka.financetracker.navigation.NavItem
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavItem.entries.forEach { item ->
            val isSelected = item.route == currentRoute

            NavigationBarItem(

                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = ImageVector.vectorResource(it),
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    item.label?.let {
                        Text(
                            text = item.label,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = LightColors.primary,
                    selectedTextColor = LightColors.onSecondary,
                    selectedIndicatorColor = LightColors.secondary,
                    unselectedIconColor = LightColors.onSecondary,
                    unselectedTextColor = LightColors.onSecondary,
                    disabledIconColor = LightColors.onPrimary,
                    disabledTextColor = LightColors.onPrimary
                )
            )
        }
    }
}
