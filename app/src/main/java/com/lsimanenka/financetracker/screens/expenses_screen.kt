package com.lsimanenka.financetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.lsimanenka.financetracker.LightColors
import com.lsimanenka.financetracker.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ListItem.LilListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.ListItem.SwitchTrail
import com.lsimanenka.financetracker.MyTopAppBar
import com.lsimanenka.financetracker.R

@Composable
fun ExpensesScreen() {
    Column {
        LilListItem(content = "Всего", money = "436558", color = LightColors.secondary)
        ListItem("\uD83D\uDC80", "Аренда квартиры", null, "100000", { IconButtonTrail() })
       ListItem("\uD83C\uDFE0", "Аренда квартиры", null, "100000", { IconButtonTrail()  })
       ListItem("\uD83C\uDFE0", "Аренда квартиры", "Harold", "100000",{ IconButtonTrail() })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", "Anny", "100000",{ IconButtonTrail() })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", null, "100000",{ IconButtonTrail() })
        ListItem("\uD83C\uDFE0", "Аренда квартиры", "Alex", "100000",{ IconButtonTrail() })

    }


}