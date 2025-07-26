package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.lsimanenka.financetracker.ui.theme.PrimaryColor
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lsimanenka.financetracker.R
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.domain.viewmodel.AccountViewModel
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent
import com.lsimanenka.financetracker.ui.theme.ThemeManager

@Composable
fun ColorPickerScreen(onColorSelected: (PrimaryColor) -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.choose_primary_color),
            style = MaterialTheme.typography.titleLarge
        )

        PrimaryColor.values().forEach { colorOption ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        ThemeManager.savePrimaryColor(context, colorOption)
                        onColorSelected(colorOption)
                    }
            ) {
                Canvas(modifier = Modifier.size(24.dp)) {
                    drawCircle(colorOption.color)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(colorOption.name, color = colorOption.color)
            }
        }
    }
}
