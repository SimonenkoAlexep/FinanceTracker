package com.lsimanenka.financetracker.ui.ListItem

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Switch
import com.lsimanenka.financetracker.ui.theme.LightColors
import java.sql.Date

private const val paddingInListItem: Int = 8

@Composable
private fun GeneralListItem(
    height: Int,
    lead: String? = null,
    content: String,
    comment: String? = null,
    money: String? = null,
    trail: @Composable (() -> Unit)? = null,
    color: Color = Color.White,
    trailContent: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .background(color = color)
            .height(height.dp)
            .border(1.dp, Color(0xFFCAC4D0))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,

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
            Spacer(modifier = Modifier.width(paddingInListItem.dp))
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

        if (money != null && trailContent != null) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$money₽",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = paddingInListItem.dp),
                    textAlign = TextAlign.End
                )
                Text(
                    text = trailContent,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = paddingInListItem.dp),
                    textAlign = TextAlign.End
                )

            }
        } else if (money != null) {
            Text(
                text = "$money₽",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = paddingInListItem.dp),

                )
        } else if (trailContent != null) {
            Text(
                text = trailContent,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = paddingInListItem.dp),

                )
        }

        if (trail != null) {
            trail()

        }
    }
}

@Composable
fun HeaderListItem(
    lead: String? = null,
    content: String,
    money: String? = null,
    trailContent: String? = null,
    trail: @Composable (() -> Unit)? = null,
    color: Color = LightColors.secondary,
    onClick: (() -> Unit)? = null
) {
    GeneralListItem(
        height = 56,
        lead = lead,
        content = content,
        money = money,
        trailContent = trailContent,
        trail = trail,
        color = color,
        onClick = onClick
    )
}

@Composable
fun ListItem(
    lead: String? = null,
    content: String,
    comment: String? = null,
    money: String? = null,
    trailContent: String? = null,
    trail: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    GeneralListItem(
        height = 72,
        lead = lead,
        content = content,
        money = money,
        trailContent = trailContent,
        trail = trail,
        comment = comment,
        onClick = onClick
    )
}
