package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel


@Composable
fun ItemsScreen(
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.state

    var searchQuery by rememberSaveable { mutableStateOf("") }

    val categories = uiState.categories.orEmpty()
    val filtered = remember(categories, searchQuery) {
        if (searchQuery.isBlank()) categories
        else categories.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.emoji.contains(searchQuery)
        }
    }

    Column {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightColors.surface),
            placeholder = { Text("Найти статью") },
            trailingIcon = { IconButtonTrail(R.drawable.ic_find) },
            singleLine = true,
        )
        LazyColumn {

            if (uiState.isLoading) {
                item { ListItem("⏳", "Загрузка…") }
            } else if (uiState.error.isNotBlank()) {
                item { ListItem("❌", "Ошибка: ${uiState.error}") }
            } else if (filtered.isEmpty()) {
                item { ListItem("ℹ️", "Ничего не найдено") }
            } else {
                items(filtered) { category ->
                    ListItem(
                        lead = category.emoji,
                        content = category.name,
                    )
                }
            }
        }

    }
}
