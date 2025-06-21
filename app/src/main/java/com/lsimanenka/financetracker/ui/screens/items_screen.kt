package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun ItemsScreen() {
    Column {
        HeaderListItem(
            content = "Найти статью",
            color = LightColors.surface,
            trail = { IconButtonTrail(R.drawable.ic_find) }
        )
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83D\uDC80", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")

    }


}