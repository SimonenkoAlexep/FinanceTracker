package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.MyColors


@Composable
fun CreatorScreen() {
    Surface(color = MyColors.background) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = stringResource(R.string.creator_name))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
