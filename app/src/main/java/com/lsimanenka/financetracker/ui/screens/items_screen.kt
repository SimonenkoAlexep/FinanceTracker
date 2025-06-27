package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.viewmodel.CategoriesViewModel

@Composable
fun ItemsScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.state

    Column {

        HeaderListItem(
            content = "Найти статью",
            color = LightColors.surface,
            trail = { IconButtonTrail(R.drawable.ic_find) }
        )

        LazyColumn {

            when {
                uiState.isLoading -> {
                    item {
                        ListItem("⏳", "Загрузка категорий…")
                    }
                }

                uiState.error.isNotBlank() -> {
                    item {
                        ListItem("❌", "Ошибка: ${uiState.error}")
                    }
                }

                uiState.categories.isNullOrEmpty() -> {
                    item {
                        ListItem("ℹ️", "Категории не найдены")
                    }
                }

                else -> {
                    items(uiState.categories!!) { category ->
                        ListItem(
                            lead = category.emoji,
                            content = category.name
                        )
                    }
                }
            }
        }
    }
}
