package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun IncomeScreen() {
    Column {
        HeaderListItem(content = "Всего", money = "600000", color = LightColors.secondary)
        ListItem(null, "Зарплата", null, "500000", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(null, "Подработка", null, "100000", trail = { IconButtonTrail(R.drawable.ic_more_vert) })


    }


}