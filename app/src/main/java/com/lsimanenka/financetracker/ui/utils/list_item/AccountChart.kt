package com.lsimanenka.financetracker.ui.utils.list_item

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@SuppressLint("NewApi")
@Composable
fun AccountChart(diffs: List<DailyDiff>) {
    val maxDiff = diffs.maxOfOrNull { it.diff.absoluteValue } ?: 1.0

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(diffs) { day ->
            val barHeight = (day.diff.absoluteValue / maxDiff * 100).dp
            val color = when {
                day.diff > 0 -> Color(0xFF4CAF50)     // Доход
                day.diff < 0 -> Color(0xFFFF5722)     // Расход
                else -> Color.Gray                    // Нейтральный день
            }

            Column(
                modifier = Modifier.height(120.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
            Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(barHeight)
                        .background(color = color, shape = RoundedCornerShape(2.dp))
                )
                Text(
                    text = day.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
