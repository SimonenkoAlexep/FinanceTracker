// CategoryDropdown.kt
package com.lsimanenka.financetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.icons.Icons
//import androidx.compose.material3.icons.filled.ArrowDropDown
//import androidx.compose.material3.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lsimanenka.financetracker.data.model.Category
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

/**
 * Пулдаун для выбора категории.
 *
 * @param categories   список всех доступных категорий
 * @param selectedId   id выбранной категории или null
 * @param onSelect     лямбда, которая возвращает id выбранной категории
 * @param modifier     модификатор для внешнего контейнера
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    //categories: List<Category>,
    selectedId: Int?,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: CategoriesViewModel = viewModel(factory = factory)

    val uiState by viewModel.state

    val categories : List<Category>? = uiState.categories

    var expanded by remember { mutableStateOf(false) }
    val selectedCategory = categories?.firstOrNull { it.id == selectedId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedCategory?.name ?: "",
            onValueChange = {  },
            readOnly = true,
            label = { Text("Статья") },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowForward else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { expanded = !expanded }
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            ),
            modifier = Modifier.fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (categories != null) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        leadingIcon = {
                            Text(
                                text = category.emoji,
                                modifier = Modifier
                            )
                        },
                        modifier = Modifier.background(Color.White).fillMaxWidth(),
                        onClick = {
                            onSelect(category.id)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
