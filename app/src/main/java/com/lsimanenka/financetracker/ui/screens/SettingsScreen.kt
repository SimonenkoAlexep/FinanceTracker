package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.lsimanenka.financetracker.ui.utils.list_item.IconButtonTrail
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.ui.theme.ThemeManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource

@Composable
fun SettingsScreen(
    onNavigateToColorPicker: () -> Unit,
    onNavigateToAppInfo: () -> Unit,
    onNavigateToCreator: () -> Unit,
    onNavigateSyncSettings: () -> Unit,
    onNavigateHapticsSettings: () -> Unit,
    onNavigateLanguageSettings: () -> Unit,
    ) {
    val context = LocalContext.current
    var isDarkTheme by rememberSaveable { mutableStateOf(ThemeManager.loadTheme(context)) }
    var primaryColor by rememberSaveable { mutableStateOf(ThemeManager.loadPrimaryColor(context)) }

    MyColors.init(isDarkTheme, primaryColor)

    key(isDarkTheme, primaryColor) {
        MaterialTheme(colorScheme = MyColors.all) {
            Surface(color = MyColors.background) {
                Column {
                    ListItem(
                        content = stringResource(R.string.dark_theme),
                        trail = {
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = {
                                    isDarkTheme = it
                                    ThemeManager.saveTheme(context, it)
                                    MyColors.init(it, primaryColor)
                                },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = MyColors.primary
                                )
                            )
                        }
                    )
                    ListItem(
                        content = stringResource(R.string.primary_color),
                        trail = { IconButtonTrail(R.drawable.ic_more_vert) },
                        onClick = onNavigateToColorPicker
                    )
                    ListItem(content = stringResource(R.string.sound), trail = { IconButtonTrail(R.drawable.ic_more_vert) })
                    ListItem(content = stringResource(R.string.haptics), trail = { IconButtonTrail(R.drawable.ic_more_vert) }, onClick = onNavigateHapticsSettings)
                    ListItem(content = stringResource(R.string.passcode), trail = { IconButtonTrail(R.drawable.ic_more_vert) })
                    ListItem(content = stringResource(R.string.sync), trail = { IconButtonTrail(R.drawable.ic_more_vert) }, onClick = onNavigateSyncSettings)
                    ListItem(content = stringResource(R.string.language), trail = { IconButtonTrail(R.drawable.ic_more_vert) }, onClick = onNavigateLanguageSettings)
                    ListItem(content = stringResource(R.string.about_app), trail = { IconButtonTrail(R.drawable.ic_more_vert) }, onClick = onNavigateToAppInfo)
                    ListItem(content = stringResource(R.string.creator), trail = { IconButtonTrail(R.drawable.ic_more_vert) }, onClick = onNavigateToCreator)
                }
            }
        }
    }
}
