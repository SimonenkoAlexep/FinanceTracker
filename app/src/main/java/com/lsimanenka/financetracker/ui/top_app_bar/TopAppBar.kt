package com.lsimanenka.financetracker.ui.top_app_bar

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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors

/** Возможные действия из TopBar */
enum class TopBarAction { EditAccount, History, Cancel, Accept, Statistics }

/**
 * Универсальный TopAppBar для всех screen-ов.
 *
 * @param currentRoute   — navBackStackEntry?.destination?.route
 * @param onAction       — колбэк на нажатия кнопок
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFor(
    currentRoute: String?,
    onAction: (TopBarAction) -> Unit = {}
) {
    val sysUi = rememberSystemUiController()
    SideEffect { sysUi.setStatusBarColor(LightColors.primary) }

    val baseRoute = currentRoute?.substringBefore("/")

    when (baseRoute) {
        Routes.EXPENSES -> {
            MyTopAppBar(
                title               = "Расходы сегодня",
                painterTrail        = painterResource(R.drawable.ic_history),
                iconDescTrail       = "История",
                onActionClickTrail  = { onAction(TopBarAction.History) }
            )
        }
        Routes.INCOME -> {
            MyTopAppBar(
                title               = "Доходы сегодня",
                painterTrail        = painterResource(R.drawable.ic_history),
                iconDescTrail       = "История",
                onActionClickTrail  = { onAction(TopBarAction.History) }
            )
        }
        Routes.ACCOUNT -> {
            MyTopAppBar(
                title               = "Мой счёт",
                painterTrail        = painterResource(R.drawable.ic_edit),
                iconDescTrail       = "Редактировать",
                onActionClickTrail  = { onAction(TopBarAction.EditAccount) }
            )
        }
        Routes.ITEMS -> {
            MyTopAppBar(title = "Мои статьи")
        }
        Routes.SETTINGS -> {
            MyTopAppBar(title = "Настройки")
        }
        Routes.EXPENSES_HISTORY -> {
            MyTopAppBar(
                title               = "История расходов",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Назад",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
                painterTrail        = painterResource(R.drawable.ic_statistics),
                iconDescTrail       = "Статистика",
                onActionClickTrail  = { onAction(TopBarAction.Statistics) }
            )
        }
        Routes.INCOME_HISTORY -> {
            MyTopAppBar(
                title               = "История доходов",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Назад",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
                painterTrail        = painterResource(R.drawable.ic_statistics),
                iconDescTrail       = "Статистика",
                onActionClickTrail  = { onAction(TopBarAction.Statistics) }
            )
        }
        Routes.EXPENSES_STATISTICS -> {
            MyTopAppBar(
                title               = "Анализ",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Назад",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
            )
        }
        Routes.INCOME_STATISTICS -> {
            MyTopAppBar(
                title               = "Анализ",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Назад",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
            )
        }
        Routes.ACCOUNT_EDIT -> {
            MyTopAppBar(
                title               = "Редактировать счёт",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Отмена",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
                painterTrail        = painterResource(R.drawable.ic_accept),
                iconDescTrail       = "Принять",
                onActionClickTrail  = { onAction(TopBarAction.Accept) }
            )
        }
        Routes.TRANSACTION_EDIT -> {
            MyTopAppBar(
                title = "Мои расходы",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Отмена",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
                painterTrail        = painterResource(R.drawable.ic_accept),
                iconDescTrail       = "Принять",
                onActionClickTrail  = { onAction(TopBarAction.Accept) }
            )
        }
        Routes.TRANSACTION_CREATE -> {
            MyTopAppBar(
                title = "Мои расходы",
                painterLead         = painterResource(R.drawable.ic_back_arrow),
                iconDescLead        = "Отмена",
                onActionClickLead   = { onAction(TopBarAction.Cancel) },
                painterTrail        = painterResource(R.drawable.ic_accept),
                iconDescTrail       = "Принять",
                onActionClickTrail  = { onAction(TopBarAction.Accept) }
            )
        }
        else -> {
            // Пустышка высотой AppBar’а
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopAppBar(
    title: String,
    painterLead: Painter?         = null,
    iconDescLead: String?         = null,
    onActionClickLead: () -> Unit = {},
    painterTrail: Painter?        = null,
    iconDescTrail: String?        = null,
    onActionClickTrail: () -> Unit = {}
) {
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
            containerColor     = LightColors.primary,
            titleContentColor  = LightColors.onSecondary
        )
    )
}
