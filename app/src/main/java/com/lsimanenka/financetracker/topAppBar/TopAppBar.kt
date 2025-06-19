package com.lsimanenka.financetracker.topAppBar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.navigation.NavItem
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun TopBarFor(route: String?): @Composable () -> Unit = when (route) {
    NavItem.EXPENSES.route -> {
        { MyTopAppBar("Расходы сегодня", painterResource(R.drawable.ic_history), "История") }
    }

    NavItem.INCOME.route -> {
        { MyTopAppBar("Доходы сегодня", painterResource(R.drawable.ic_history), "История") }
    }

    NavItem.ACCOUNT.route -> {
        { MyTopAppBar("Мой счёт", painterResource(R.drawable.ic_edit), "Редактировать") }
    }

    NavItem.ITEMS.route -> {
        { MyTopAppBar("Мои статьи", null, null) }
    }

    NavItem.SETTINGS.route -> {
        { MyTopAppBar("Настройки", null, null) }
    }

    else -> {
        { }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    painter: Painter?,
    iconDescription: String?
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(LightColors.primary)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 22.sp,
                color = LightColors.onSecondary
            )
        },

        navigationIcon = {
            Spacer(Modifier.width(48.dp))
        },

        actions = {
            painter?.let {
                IconButton(onClick = { /*...*/ }) {
                    Icon(
                        painter = it,
                        contentDescription = iconDescription,
                        tint = LightColors.onSecondary
                    )
                }
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightColors.primary,
            titleContentColor = LightColors.onSecondary
        )
    )
}
