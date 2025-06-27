package com.lsimanenka.financetracker.ui.topAppBar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun TopBarFor(
    navController: NavHostController,
    currentRoute: String?
) {
    when (currentRoute) {
        Routes.EXPENSES -> {
            MyTopAppBar(
                title = "Расходы сегодня",
                painterTrail = painterResource(R.drawable.ic_history),
                iconDescTrail = "История",
                onActionClickTrail = {
                    navController.navigate(Routes.EXPENSES_HISTORY)
                }
            )
        }
        Routes.INCOME -> {
            MyTopAppBar(
                title = "Доходы сегодня",
                painterTrail = painterResource(R.drawable.ic_history),
                iconDescTrail = "История",
                onActionClickTrail = { navController.navigate(Routes.INCOME_HISTORY) }
            )
        }

        Routes.ACCOUNT -> {
            MyTopAppBar(
                title = "Мой счёт",
                painterTrail = painterResource(R.drawable.ic_edit),
                iconDescTrail = "Редактировать",
                onActionClickTrail = { /*…*/ }
            )
        }

        Routes.ITEMS -> {
            MyTopAppBar("Мои статьи")
        }

        Routes.SETTINGS -> {
            MyTopAppBar("Настройки")
        }

        Routes.EXPENSES_HISTORY -> {
            MyTopAppBar(
                "Моя история",
                painterResource(R.drawable.ic_back_arrow),
                "Назад",
                { navController.navigate(Routes.EXPENSES) },
                painterResource(R.drawable.ic_statistics),
                "Статистика",
            )
        }

        Routes.INCOME_HISTORY -> {
            MyTopAppBar(
                "Моя история",
                painterResource(R.drawable.ic_back_arrow),
                "Назад",
                { navController.navigate(Routes.INCOME) },
                painterResource(R.drawable.ic_statistics),
                "Статистика",
            )
        }

        else -> {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    painterLead: Painter? = null,
    iconDescLead: String? = null,
    onActionClickLead: () -> Unit = {},
    painterTrail: Painter? = null,
    iconDescTrail: String? = null,
    onActionClickTrail: () -> Unit = {},
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(LightColors.primary)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(title, fontSize = 22.sp, color = LightColors.onSecondary)
        },
        navigationIcon = {
            painterLead?.let {
                IconButton(onClick = onActionClickLead) {
                    Icon(it, contentDescription = iconDescLead, tint = LightColors.onSecondary)
                }
            }
        },
        actions = {
            painterTrail?.let {
                IconButton(onClick = onActionClickTrail) {
                    Icon(it, contentDescription = iconDescTrail, tint = LightColors.onSecondary)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightColors.primary,
            titleContentColor = LightColors.onSecondary
        )
    )
}
