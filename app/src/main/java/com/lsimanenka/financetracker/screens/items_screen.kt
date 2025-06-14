package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.lsimanenka.financetracker.LightColors
import com.lsimanenka.financetracker.ListItem.LilListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.MyTopAppBar
import com.lsimanenka.financetracker.R

@Composable
fun ItemsScreen() {
    Column {
        LilListItem(content = "Найти статью", color = LightColors.surface, trail = R.drawable.ic_find)
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83D\uDC80", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")
        ListItem("\uD83C\uDFE0", "Аренда квартиры")

    }


}