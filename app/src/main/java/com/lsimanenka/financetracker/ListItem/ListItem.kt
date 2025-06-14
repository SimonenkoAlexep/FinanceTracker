package com.lsimanenka.financetracker.ListItem

import android.widget.Switch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lsimanenka.financetracker.LightColors
import com.lsimanenka.financetracker.R
import androidx.compose.material3.Switch



@Composable
fun LilListItem(
    lead: String? = null,
    content: String,
    money: String? = null,
    trail: Any? = null,
    color: Color = Color.White
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = color)
            .height(56.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (lead != null) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = LightColors.onPrimary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lead,
                    fontSize = 18.sp,
                    color = LightColors.onSecondary,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(content, fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        Spacer(Modifier.weight(1f))
        if (money != null) {
            Text(
                money + "₽",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
        if (trail is Int) {
            IconButton(onClick = { /*...*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(trail),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ListItem(
    lead: String? = null,
    content: String,
    comment: String? = null,
    money: String? = null,
    trail: @Composable (() -> Unit)? = null
) {
    Row(
        Modifier
            .height(70.dp)
            .border(1.dp, Color(0xFFCAC4D0))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1) Если lead не null — рисуем кружок с эмодзи/текстом
        if (lead != null) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = LightColors.secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lead,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        // 2) Основной контент + опциональный комментарий
        if (comment == null) {
            Text(content, fontSize = 16.sp)
        } else {
            Column {
                Text(content, fontSize = 16.sp)
                Text(comment, fontSize = 14.sp, color = Color(0xFF49454F))
            }
        }

        Spacer(Modifier.weight(1f))

        // 3) Деньги
        if (money != null) {
            Text(
                text = "$money₽",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // 4) trail как иконка
        if (trail != null) {
            trail()

        }
       /* if (trail is Switch) {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = LightColors.primary
                )
            )
        }*/
    }
}

@Composable
fun IconButtonTrail() {
    IconButton(onClick = { /*...*/ }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert),
            contentDescription = null
        )
    }
}

@Composable
fun SwitchTrail() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { isDarkTheme = it },
        colors = SwitchDefaults.colors(
            checkedTrackColor = LightColors.primary
        )
    )
}

