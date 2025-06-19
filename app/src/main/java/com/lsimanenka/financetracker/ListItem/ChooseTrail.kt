package com.lsimanenka.financetracker.ListItem

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.lsimanenka.financetracker.ui.theme.LightColors


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
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { isDarkTheme = it },
        colors = SwitchDefaults.colors(
            checkedTrackColor = LightColors.primary
        )
    )
}
