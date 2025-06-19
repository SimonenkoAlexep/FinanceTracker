package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun AccountScreen() {
    Column {
        HeaderListItem(
            lead = "\uD83D\uDCB0",
            content = "Баланс",
            money = "-670000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) },
            color = LightColors.secondary
        )
        HeaderListItem(
            content = "Валюта",
            money = "",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) },
            color = LightColors.secondary
        )
    }


}