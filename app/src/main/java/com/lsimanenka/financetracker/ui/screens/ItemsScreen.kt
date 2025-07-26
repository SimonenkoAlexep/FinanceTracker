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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.IconButtonTrail
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

@Composable
fun ItemsScreen() {
    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: CategoriesViewModel = viewModel(factory = factory)

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
                .background(MyColors.surface),
            placeholder = { Text(stringResource(R.string.search_category)) },
            trailingIcon = { IconButtonTrail(R.drawable.ic_find) },
            singleLine = true,
        )

        LazyColumn {
            if (uiState.isLoading) {
                item { ListItem("⏳", stringResource(R.string.loading)) }
            } else if (uiState.error.isNotBlank()) {
                item { ListItem("❌", "${stringResource(R.string.error_prefix)}: ${uiState.error}") }
            } else if (filtered.isEmpty()) {
                item { ListItem("ℹ️", stringResource(R.string.nothing_found)) }
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
