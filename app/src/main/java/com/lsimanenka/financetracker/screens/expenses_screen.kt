package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors

@Composable
fun ExpensesScreen() {
    Column {
        HeaderListItem(content = "Всего", money = "436558", color = LightColors.secondary)
        ListItem("\uD83D\uDC80", "Аренда квартиры", null, "100000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", null, "100000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", "Harold", "100000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", "Anny", "100000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", null, "100000", { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", "Alex", "100000", { IconButtonTrail(R.drawable.ic_more_vert) })

    }


}