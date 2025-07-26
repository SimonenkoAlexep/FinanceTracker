package com.lsimanenka.financetracker.ui.utils.list_item

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.ui.theme.ThemeManager
import com.lsimanenka.financetracker.ui.theme.toggleTheme


@Composable
fun IconButtonTrail(trail: Int) {
    IconButton(onClick = { /*...*/ }) {
        Icon(
            imageVector = ImageVector.vectorResource(trail),
            contentDescription = null
        )
    }
}

@Composable
fun SwitchTrail() {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(ThemeManager.loadTheme(context)) }

    Switch(
        checked = isDarkTheme,
        onCheckedChange = {
            isDarkTheme = it
            toggleTheme(context, it) // сохраняет в SharedPreferences
        },
        colors = SwitchDefaults.colors(
            checkedTrackColor = MyColors.primary
        )
    )

}





