package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun ExpensesScreen() {
    Column {
        HeaderListItem(content = "Всего", money = "436558", color = LightColors.secondary)
        ListItem(
            lead = "\uD83D\uDC80",
            content = "Аренда квартиры",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = null,
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Harold",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Anny",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = null,
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Alex",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })

    }


}