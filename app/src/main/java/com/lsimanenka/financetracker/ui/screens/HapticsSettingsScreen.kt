package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.HapticEffect
import com.lsimanenka.financetracker.ui.theme.ThemeManager
@Composable
fun HapticsSettingsScreen() {
    val context = LocalContext.current
    var isEnabled by rememberSaveable { mutableStateOf(ThemeManager.isVibrationEnabled(context)) }
    var selectedEffect by rememberSaveable { mutableStateOf(ThemeManager.loadEffect(context)) }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = stringResource(R.string.haptics_title), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        Switch(
            checked = isEnabled,
            onCheckedChange = {
                isEnabled = it
                ThemeManager.setVibrationEnabled(context, it)
            }
        )

        Spacer(Modifier.height(24.dp))

        if (isEnabled) {
            Text(stringResource(R.string.choose_haptic_effect), style = MaterialTheme.typography.bodyLarge)
            HapticEffect.values().forEach { effect ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedEffect = effect
                            ThemeManager.setEffect(context, effect)
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = effect == selectedEffect, onClick = null)
                    Spacer(Modifier.width(8.dp))
                    Text(effect.label)
                }
            }
        }
    }
}
