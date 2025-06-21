package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.ui.ListItem.SwitchTrail
import com.lsimanenka.financetracker.R

@Composable
fun SettingsScreen() {
    Column {
        ListItem(content = "Тёмная тема", trail = { SwitchTrail() })
        ListItem(content = "Основной цвет", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "Звуки", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "Хаптики", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "Код пароль", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "Синхронизация", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "Язык", trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(content = "О программе", trail = { IconButtonTrail(R.drawable.ic_more_vert) })

    }


}