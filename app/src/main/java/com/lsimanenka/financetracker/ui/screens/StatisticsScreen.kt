package com.lsimanenka.financetracker.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.utils.list_item.IconButtonTrail
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.domain.viewmodel.HistoryViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.math.roundToInt
@SuppressLint("NewApi")
@Composable
fun StatisticsScreen(
    onTransactionClick: (Int) -> Unit = {},
    isIncome: Boolean
) {
    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: HistoryViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        viewModel.setIsIncome(isIncome)
    }

    val uiState by viewModel.state
    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val transactions = uiState.transactions

    data class CategoryGroup(
        val emoji: String,
        val name: String,
        val transactions: List<TransactionResponse>,
        val total: Double
    )

    val categoryGroups = remember(transactions) {
        transactions
            .groupBy { it.category }
            .map { (category, txs) ->
                CategoryGroup(
                    emoji = category.emoji,
                    name = category.name,
                    transactions = txs,
                    total = txs.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
                )
            }
    }

    val total = transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }

    val pieData = categoryGroups.map { it.name to ((it.total / total) * 100f).toFloat() }
    val pieColors = listOf(
        Color(0xFF4CAF50),
        Color(0xFFFFC107),
        Color(0xFF2196F3),
        Color(0xFFF44336),
        Color(0xFF9C27B0),
        Color(0xFF009688),
        Color(0x884CAF50),
        Color(0x88FFC107),
        Color(0x882196F3),
        Color(0x88F44336),
        Color(0x889C27B0),
        Color(0x88009688),
        Color(0xFFFFAF50),
        Color(0xFF00C107),
        Color(0xFFFF96F3),
        Color(0xFF004336),
        Color(0xFF0027B0),
        Color(0xFFFF9688),
        )

    if (showStartPicker) {
        val cal = Calendar.getInstance().apply {
            set(startDate.year, startDate.monthValue - 1, startDate.dayOfMonth)
        }
        DatePickerDialog(
            context,
            { _, y, m, d ->
                viewModel.onStartDatePicked(LocalDate.of(y, m + 1, d))
                showStartPicker = false
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener { showStartPicker = false }
        }.show()
    }

    if (showEndPicker) {
        val cal = Calendar.getInstance().apply {
            set(endDate.year, endDate.monthValue - 1, endDate.dayOfMonth)
        }
        DatePickerDialog(
            context,
            { _, y, m, d ->
                viewModel.onEndDatePicked(LocalDate.of(y, m + 1, d))
                showEndPicker = false
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener { showEndPicker = false }
        }.show()
    }

    Column(Modifier.fillMaxSize()) {
        ListItem(
            content = stringResource(R.string.start_date),
            trailContent = startDate.format(dateFmt),
            onClick = { showStartPicker = true }
        )
        ListItem(
            content = stringResource(R.string.end_date),
            trailContent = endDate.format(dateFmt),
            onClick = { showEndPicker = true }
        )

        ListItem(
            content = stringResource(R.string.total),
            money = "% .0f".format(total)
        )

        DonutChart(
            data = pieData,
            colors = pieColors,
            modifier = Modifier
                .height(220.dp)
                .width(220.dp)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )


        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error.isNotBlank() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("${stringResource(R.string.error_prefix)}: ${stringResource(R.string.nothing_found)}")
                }
            }
            categoryGroups.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.nothing_found))
                }
            }
            else -> {
                LazyColumn(Modifier.fillMaxSize()) {
                    categoryGroups
                        .sortedByDescending { it.total }
                        .forEach { group ->
                            item {
                                ListItem(
                                    lead         = group.emoji,
                                    content      = group.name,
                                    money        = "%.0f".format(group.total),
                                    trailContent = "${(group.total / total * 100).roundToInt()}%",
                                    trail        = { IconButtonTrail(R.drawable.ic_more_vert) }
                                )
                            }
                        }
                }
            }
        }
    }
}
@Composable
fun DonutChart(
    data: List<Pair<String, Float>>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val thickness = size.minDimension / 14f
            var startAngle = -90f

            data.forEachIndexed { index, (_, value) ->
                val sweep = 360 * (value / 100)
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = thickness, cap = StrokeCap.Square)
                )
                startAngle += sweep
            }
        }

        Box(
            modifier = Modifier
                .height(220.dp)
                .width(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(data.size) { index ->
                    val (label, _) = data[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(colors[index % colors.size], shape = CircleShape)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
