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
fun AccountScreen() {
    Column {
        LilListItem(lead = "\uD83D\uDCB0",content = "Баланс", money = "-670000", trail = R.drawable.ic_more_vert, color = LightColors.secondary)
        LilListItem(content = "Валюта", money = "", trail = R.drawable.ic_more_vert, color = LightColors.secondary)

    }


}