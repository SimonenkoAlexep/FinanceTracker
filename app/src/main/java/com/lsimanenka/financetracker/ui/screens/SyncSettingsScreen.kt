package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.data.worker.scheduleSyncOnConnect
import com.lsimanenka.financetracker.ui.theme.ThemeManager

@Composable
fun SyncSettingsScreen() {
    val context = LocalContext.current
    var intervalHours by rememberSaveable {
        mutableStateOf(ThemeManager.loadInterval(context))
    }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = stringResource(R.string.sync_interval_label, intervalHours),
            style = MaterialTheme.typography.titleLarge
        )

        Slider(
            value = intervalHours.toFloat(),
            valueRange = 1f..24f,
            steps = 22,
            onValueChange = { intervalHours = it.toInt() },
            onValueChangeFinished = {
                ThemeManager.saveInterval(context, intervalHours)
                scheduleSyncOnConnect(context, intervalHours)
            }
        )
    }
}
