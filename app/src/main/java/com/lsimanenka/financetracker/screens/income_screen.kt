package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun IncomeScreen() {
    Column {
        HeaderListItem(content = "Всего", money = "600000", color = LightColors.secondary)
        ListItem(null, "Зарплата", null, "500000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(null, "Подработка", null, "100000", { IconButtonTrail(R.drawable.ic_more_vert) })


    }


}