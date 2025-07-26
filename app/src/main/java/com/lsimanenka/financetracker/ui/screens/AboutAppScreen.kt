package com.lsimanenka.financetracker.ui.screens

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lsimanenka.financetracker.data.AppInfoProvider
import com.lsimanenka.financetracker.ui.theme.MyColors
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.R

@Composable
fun AboutAppScreen() {
    val context = LocalContext.current
    val version = remember { AppInfoProvider.getAppVersion(context) }
    val updateDate = remember { AppInfoProvider.getLastUpdateDate(context) }

    Surface(color = MyColors.background) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(stringResource(R.string.about_title), style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            Text("${stringResource(R.string.about_version)}: $version", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("${stringResource(R.string.about_last_update)}: $updateDate", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
