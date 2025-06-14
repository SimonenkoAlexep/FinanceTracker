package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.lsimanenka.financetracker.LightColors
import com.lsimanenka.financetracker.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ListItem.LilListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.MyTopAppBar
import com.lsimanenka.financetracker.R

@Composable
fun IncomeScreen() {
    Column {
        LilListItem(content = "Всего", money = "600000", color = LightColors.secondary)
        ListItem(null, "Зарплата", null, "500000",{ IconButtonTrail() })
        ListItem(null, "Подработка", null, "100000",{ IconButtonTrail() })


    }


}