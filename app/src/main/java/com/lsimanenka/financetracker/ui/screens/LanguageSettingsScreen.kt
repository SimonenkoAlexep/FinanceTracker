package com.lsimanenka.financetracker.ui.screens

import android.app.Activity
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.PrimaryColor
import com.lsimanenka.financetracker.ui.theme.ThemeManager
@Composable
fun LanguageSettingsScreen(
    onLanguageSelected: () -> Unit
) {
    val context = LocalContext.current
    val currentLocale = ThemeManager.getLocale(context).language
    val options = listOf("ru" to stringResource(R.string.lang_russian), "en" to stringResource(R.string.lang_english))

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = stringResource(R.string.choose_language), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        options.forEach { (code, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        ThemeManager.saveLocale(context, code)
                        (context as? Activity)?.recreate()
                        onLanguageSelected()
                        //(context as? Activity)?.recreate()
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = currentLocale == code, onClick = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label)
            }
        }
    }
}
