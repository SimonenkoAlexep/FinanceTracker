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
fun SettingsScreen() {
    Column {
        ListItem(content = "Тёмная тема", trail = { SwitchTrail() })
        ListItem(content = "Основной цвет", trail = { IconButtonTrail() })
        ListItem(content = "Звуки", trail = { IconButtonTrail() })
        ListItem(content = "Хаптики", trail = { IconButtonTrail() })
        ListItem(content = "Код пароль", trail = { IconButtonTrail() })
        ListItem(content = "Синхронизация", trail = { IconButtonTrail() })
        ListItem(content = "Язык", trail = { IconButtonTrail() })
        ListItem(content = "О программе", trail = { IconButtonTrail() })

    }


}