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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.MyColors

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
    SideEffect { sysUi.setStatusBarColor(MyColors.primary) }

    val baseRoute = currentRoute?.substringBefore("/")
    val context = LocalContext.current

    when (baseRoute) {
        Routes.EXPENSES -> {
            MyTopAppBar(
                title = stringResource(R.string.expenses_today),
                painterTrail = painterResource(R.drawable.ic_history),
                iconDescTrail = stringResource(R.string.history),
                onActionClickTrail = { onAction(TopBarAction.History) }
            )
        }

        Routes.INCOME -> {
            MyTopAppBar(
                title = stringResource(R.string.income_today),
                painterTrail = painterResource(R.drawable.ic_history),
                iconDescTrail = stringResource(R.string.history),
                onActionClickTrail = { onAction(TopBarAction.History) }
            )
        }

        Routes.ACCOUNT -> {
            MyTopAppBar(
                title = stringResource(R.string.account),
                painterTrail = painterResource(R.drawable.ic_edit),
                iconDescTrail = stringResource(R.string.edit),
                onActionClickTrail = { onAction(TopBarAction.EditAccount) }
            )
        }

        Routes.ITEMS -> {
            MyTopAppBar(title = stringResource(R.string.items))
        }

        Routes.SETTINGS,
        Routes.COLOR_PICKER,
        Routes.APP_INFO,
        Routes.CREATOR,
        Routes.LANGUAGE_SETTINGS -> {
            MyTopAppBar(title = stringResource(R.string.settings))
        }

        Routes.SYNC_SETTINGS -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.cancel),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_accept),
                iconDescTrail = stringResource(R.string.accept),
                onActionClickTrail = { onAction(TopBarAction.Accept) }
            )
        }

        Routes.HAPTICS_SETTINGS -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.cancel),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
            )
        }

        Routes.EXPENSES_HISTORY -> {
            MyTopAppBar(
                title = stringResource(R.string.expenses_history),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.back),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_statistics),
                iconDescTrail = stringResource(R.string.statistics),
                onActionClickTrail = { onAction(TopBarAction.Statistics) }
            )
        }

        Routes.INCOME_HISTORY -> {
            MyTopAppBar(
                title = stringResource(R.string.income_history),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.back),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_statistics),
                iconDescTrail = stringResource(R.string.statistics),
                onActionClickTrail = { onAction(TopBarAction.Statistics) }
            )
        }

        Routes.EXPENSES_STATISTICS,
        Routes.INCOME_STATISTICS -> {
            MyTopAppBar(
                title = stringResource(R.string.analytics),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.back),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
            )
        }

        Routes.ACCOUNT_EDIT -> {
            MyTopAppBar(
                title = stringResource(R.string.edit_account),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.cancel),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_accept),
                iconDescTrail = stringResource(R.string.accept),
                onActionClickTrail = { onAction(TopBarAction.Accept) }
            )
        }

        Routes.TRANSACTION_EDIT,
        Routes.TRANSACTION_CREATE -> {
            MyTopAppBar(
                title = stringResource(R.string.transaction),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = stringResource(R.string.cancel),
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_accept),
                iconDescTrail = stringResource(R.string.accept),
                onActionClickTrail = { onAction(TopBarAction.Accept) }
            )
        }

        Routes.COLOR_PICKER -> {
            MyTopAppBar(title = stringResource(R.string.settings),)
        }

        Routes.APP_INFO -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = "Отмена",
                onActionClickLead = { onAction(TopBarAction.Cancel) },
            )
        }

        Routes.CREATOR -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = "Отмена",
                onActionClickLead = { onAction(TopBarAction.Cancel) },
            )
        }

        Routes.SYNC_SETTINGS -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = "Отмена",
                onActionClickLead = { onAction(TopBarAction.Cancel) },
                painterTrail = painterResource(R.drawable.ic_accept),
                iconDescTrail = "Принять",
                onActionClickTrail = { onAction(TopBarAction.Cancel) }
            )
        }

        Routes.HAPTICS_SETTINGS -> {
            MyTopAppBar(
                title = stringResource(R.string.settings),
                painterLead = painterResource(R.drawable.ic_back_arrow),
                iconDescLead = "Отмена",
                onActionClickLead = { onAction(TopBarAction.Cancel) },
            )
        }

        Routes.LANGUAGE_SETTINGS -> {
            MyTopAppBar(title = stringResource(R.string.settings),)
        }

        else -> {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopAppBar(
    title: String,
    painterLead: Painter? = null,
    iconDescLead: String? = null,
    onActionClickLead: () -> Unit = {},
    painterTrail: Painter? = null,
    iconDescTrail: String? = null,
    onActionClickTrail: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(title, fontSize = 22.sp, color = MyColors.onSecondary)
        },
        navigationIcon = {
            painterLead?.let {
                IconButton(onClick = onActionClickLead) {
                    Icon(it, contentDescription = iconDescLead, tint = MyColors.onSecondary)
                }
            }
        },
        actions = {
            painterTrail?.let {
                IconButton(onClick = onActionClickTrail) {
                    Icon(it, contentDescription = iconDescTrail, tint = MyColors.onSecondary)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MyColors.primary,
            titleContentColor = MyColors.onSecondary
        )
    )
}
